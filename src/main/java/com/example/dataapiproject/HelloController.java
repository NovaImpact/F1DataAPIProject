package com.example.dataapiproject;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HelloController {

    @FXML private TableView<F1DriverResult> dataTableView;
    @FXML private TableColumn<F1DriverResult, String> positionColumn;
    @FXML private TableColumn<F1DriverResult, String> driverColumn;
    @FXML private TableColumn<F1DriverResult, String> teamColumn;
    @FXML private TableColumn<F1DriverResult, String> timeColumn;
    @FXML private TableColumn<F1DriverResult, String> gapColumn;
    @FXML private Button fetchButton;
    @FXML private Label statusLabel;
    @FXML private Label trackLabel;
    @FXML private ComboBox<F1SessionData> sessionComboBox;

    private ArrayList<F1SessionData> allSessions;
    private F1SessionData currentSession;

    @FXML
    public void initialize() {
        allSessions = new ArrayList<>();

        allSessions.add(new F1SessionData(
                "Gulf Air Bahrain Grand Prix 2024",
                "Practice 1",
                "https://f1api.dev/api/2024/1/fp1"
        ));
        allSessions.add(new F1SessionData(
                "Gulf Air Bahrain Grand Prix 2024",
                "Practice 2",
                "https://f1api.dev/api/2024/1/fp2"
        ));
        allSessions.add(new F1SessionData(
                "Gulf Air Bahrain Grand Prix 2024",
                "Practice 3",
                "https://f1api.dev/api/2024/1/fp3"
        ));
        allSessions.add(new F1SessionData(
                "Gulf Air Bahrain Grand Prix 2024",
                "Qualifying",
                "https://f1api.dev/api/2024/1/qualy"
        ));
        allSessions.add(new F1SessionData(
                "Gulf Air Bahrain Grand Prix 2024",
                "Race",
                "https://f1api.dev/api/2024/1/race"
        ));

        sessionComboBox.setItems(FXCollections.observableArrayList(allSessions));
        sessionComboBox.getSelectionModel().selectFirst();
        currentSession = sessionComboBox.getValue();

        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        driverColumn.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        gapColumn.setCellValueFactory(new PropertyValueFactory<>("gap"));

        updateTrackLabel();

        statusLabel.setText("Select a session and click 'Fetch Data'");
    }

    @FXML
    private void handleSessionChange() {
        currentSession = sessionComboBox.getValue();
        updateTrackLabel();

        if (!currentSession.getResults().isEmpty()) {
            displayDataInTable();
            statusLabel.setText("Showing cached data for " + currentSession.getSessionType());
        } else {
            dataTableView.setItems(FXCollections.observableArrayList());
            statusLabel.setText("Click 'Fetch Data' to load " + currentSession.getSessionType());
        }
    }

    private void updateTrackLabel() {
        trackLabel.setText(currentSession.getTrackName() + " - " + currentSession.getSessionType());
    }

    @FXML
    private void handleFetchData() {
        if (currentSession == null) {
            statusLabel.setText("Please select a session");
            return;
        }

        fetchButton.setDisable(true);
        statusLabel.setText("Fetching " + currentSession.getSessionType() + " data...");

        try {
            String jsonData = fetchDataFromAPI(currentSession.getApiUrl());

            parseAndStoreData(jsonData, currentSession);

            displayDataInTable();

            statusLabel.setText("Loaded " + currentSession.getResults().size() +
                    " drivers from " + currentSession.getSessionType());

        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fetchButton.setDisable(false);
        }
    }

    private String fetchDataFromAPI(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
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

    private void parseAndStoreData(String jsonString, F1SessionData session) {
        // Clear existing data for this session
        session.clearResults();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject races = jsonObject.getJSONObject("races");

            String arrayKey = getJsonArrayKey(session.getSessionType());
            JSONArray resultsArray = races.getJSONArray(arrayKey);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject result = resultsArray.getJSONObject(i);

                String position = String.valueOf(i + 1);

                JSONObject driver = result.getJSONObject("driver");
                String driverName = driver.getString("name") + " " +
                        driver.getString("surname");

                String team = result.getJSONObject("team").getString("teamName");
                String time = result.optString("time", "No time");
                String gap = (i == 0) ? "—" : result.optString("gap", "+0.000");

                F1DriverResult driverResult = new F1DriverResult(
                        position, driverName, team, time, gap
                );
                session.addResult(driverResult);
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error parsing JSON: " + e.getMessage());
        }
    }

    private String getJsonArrayKey(String sessionType) {
        switch (sessionType) {
            case "Practice 1": return "fp1Results";
            case "Practice 2": return "fp2Results";
            case "Practice 3": return "fp3Results";
            case "Qualifying": return "qualifyingResults";
            case "Race": return "raceResult";
            default: return "fp1Results";
        }
    }

    private void displayDataInTable() {
        dataTableView.setItems(
                FXCollections.observableArrayList(currentSession.getResults())
        );
    }

    public void printAllSessions() {
        System.out.println("=== All F1 Sessions ===");
        for (F1SessionData session : allSessions) {
            System.out.println("\n" + session);
            System.out.println("Results: " + session.getResults().size());
            for (F1DriverResult result : session.getResults()) {
                System.out.println("  " + result);
            }
        }
    }
}