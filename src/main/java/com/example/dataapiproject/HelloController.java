package com.example.dataapiproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HelloController {

    @FXML
    private TableView<F1ApiData> dataTableView;

    @FXML
    private TableColumn<F1ApiData, String> positionColumn;

    @FXML
    private TableColumn<F1ApiData, String> driverColumn;

    @FXML
    private TableColumn<F1ApiData, String> teamColumn;

    @FXML
    private TableColumn<F1ApiData, String> timeColumn;

    @FXML
    private TableColumn<F1ApiData, String> gapColumn;

    @FXML
    private Button fetchButton;

    @FXML
    private Label statusLabel;

    private ObservableList<F1ApiData> f1DataList;


    public void initialize() {
        f1DataList = FXCollections.observableArrayList();

        // Set up table columns to use F1ApiData properties
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        driverColumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        gapColumn.setCellValueFactory(new PropertyValueFactory<>("gap"));

        statusLabel.setText("Click 'Fetch F1 Data' to load practice session results");
    }


    @FXML
    private void handleFetchData() {
        try {
            statusLabel.setText("Fetching data from F1 API...");
            fetchButton.setDisable(true);

            String yourAPIurl = "https://f1api.dev/api/2024/1/fp1";
            String yourAPIkey = "";

            URL APIurl = new URL(yourAPIurl);
            HttpURLConnection APIconnection = (HttpURLConnection) APIurl.openConnection();
            APIconnection.setRequestMethod("GET");
            APIconnection.setRequestProperty("Accept", "application/json");

            if (yourAPIkey != null && !yourAPIkey.isEmpty()) {
                APIconnection.setRequestProperty("Authorization", yourAPIkey);
            }

            int responseCode = APIconnection.getResponseCode();
            if (responseCode != 200) {
                statusLabel.setText("Error: HTTP " + responseCode);
                fetchButton.setDisable(false);
                return;
            }

            InputStreamReader APIinStream = new InputStreamReader(APIconnection.getInputStream());
            BufferedReader APIreader = new BufferedReader(APIinStream);
            StringBuilder JSONstring = new StringBuilder();
            String line;

            while ((line = APIreader.readLine()) != null) {
                JSONstring.append(line);
            }
            APIreader.close();

            System.out.println("JSON Response: " + JSONstring.toString());

            parseJSONData(JSONstring.toString());

            displayData();

            statusLabel.setText("Successfully loaded " + f1DataList.size() + " drivers from 2024 F1 Practice Session");

        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fetchButton.setDisable(false);
        }
    }


    private void parseJSONData(String jsonString) {
        f1DataList.clear();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONObject races = jsonObject.getJSONObject("races");
            JSONArray resultsArray = races.getJSONArray("fp1Results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject result = resultsArray.getJSONObject(i);

                JSONObject driver = result.getJSONObject("driver");
                String driverName = driver.getString("name") + " " + driver.getString("surname");

                JSONObject team = result.getJSONObject("team");
                String teamName = team.getString("teamName");

                String position = String.valueOf(i + 1); // Position based on order in array
                String time = result.optString("time", "No time");

                String gap;
                if (i == 0) {
                    gap = "—";
                } else {
                    gap = "+0.000";
                }

                F1ApiData f1Data = new F1ApiData(driverName, teamName, position, time, gap);
                f1DataList.add(f1Data);
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error parsing JSON: " + e.getMessage());
        }
    }


    private void displayData() {
        dataTableView.setItems(f1DataList);
    }
}