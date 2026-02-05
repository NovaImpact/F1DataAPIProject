package com.example.dataapiproject;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HelloController {

    @FXML private TableView<F1ApiData> dataTableView;
    @FXML private TableColumn<F1ApiData, String> positionColumn;
    @FXML private TableColumn<F1ApiData, String> driverColumn;
    @FXML private TableColumn<F1ApiData, String> teamColumn;
    @FXML private TableColumn<F1ApiData, String> timeColumn;
    @FXML private TableColumn<F1ApiData, String> gapColumn;
    @FXML private Button fetchButton;
    @FXML private Label statusLabel;

    private ArrayList<F1ApiData> f1DataList;

    @FXML
    public void initialize() {
        f1DataList = new ArrayList<>();

        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        driverColumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        gapColumn.setCellValueFactory(new PropertyValueFactory<>("gap"));

        statusLabel.setText("Ready to fetch F1 data");
    }

    @FXML
    private void handleFetchData() {
        fetchButton.setDisable(true);
        statusLabel.setText("Fetching data...");

        try {
            String jsonData = fetchDataFromAPI();

            parseAndStoreData(jsonData);

            displayDataInTable();

            statusLabel.setText("Loaded " + f1DataList.size() + " drivers");

        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fetchButton.setDisable(false);
        }
    }

    private String fetchDataFromAPI() throws Exception {
        String apiUrl = "https://f1api.dev/api/2024/1/fp1";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }
        reader.close();

        return jsonString.toString();
    }

    private void parseAndStoreData(String jsonString) {
        f1DataList.clear();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray resultsArray = jsonObject.getJSONObject("races")
                    .getJSONArray("fp1Results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject result = resultsArray.getJSONObject(i);

                String position = String.valueOf(i + 1);

                JSONObject driver = result.getJSONObject("driver");
                String driverName = driver.getString("name") + " " +
                        driver.getString("surname");

                String team = result.getJSONObject("team").getString("teamName");
                String time = result.optString("time", "No time");
                String gap = (i == 0) ? "—" : "+0.000";

                F1ApiData data = new F1ApiData(position, driverName, team, time, gap);
                f1DataList.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayDataInTable() {
        dataTableView.setItems(FXCollections.observableArrayList(f1DataList));
    }

    public void printAllData() {
        System.out.println("=== F1 Practice Session Data ===");
        for (F1ApiData data : f1DataList) {
            System.out.println(data);
        }
    }
}