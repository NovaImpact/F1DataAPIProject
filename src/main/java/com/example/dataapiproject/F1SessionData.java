package com.example.dataapiproject;

import java.util.ArrayList;

public class F1SessionData {
    private String trackName;
    private String sessionType;
    private String apiUrl;
    private ArrayList<F1DriverResult> results;

    public F1SessionData(String trackName, String sessionType, String apiUrl) {
        this.trackName = trackName;
        this.sessionType = sessionType;
        this.apiUrl = apiUrl;
        this.results = new ArrayList<>();
    }

    public String getTrackName() { return trackName; }
    public String getSessionType() { return sessionType; }
    public String getApiUrl() { return apiUrl; }
    public ArrayList<F1DriverResult> getResults() { return results; }

    public void setResults(ArrayList<F1DriverResult> results) {
        this.results = results;
    }

    public void addResult(F1DriverResult result) {
        this.results.add(result);
    }

    public void clearResults() {
        this.results.clear();
    }

    @Override
    public String toString() {
        return trackName + " - " + sessionType;
    }
}