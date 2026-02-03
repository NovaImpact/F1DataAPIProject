package com.example.dataapiproject;


public class F1ApiData {
    private String driverName;
    private String team;
    private String position;
    private String time;
    private String gap;
    
    // Constructor
    public F1ApiData(String driverName, String team, String position, String time, String gap) {
        this.driverName = driverName;
        this.team = team;
        this.position = position;
        this.time = time;
        this.gap = gap;
    }
    
    // Default constructor
    public F1ApiData() {
        this.driverName = "";
        this.team = "";
        this.position = "";
        this.time = "";
        this.gap = "";
    }
    
    // Getters
    public String getDriverName() {
        return driverName;
    }
    
    public String getTeam() {
        return team;
    }
    
    public String getPosition() {
        return position;
    }
    
    public String getTime() {
        return time;
    }
    
    public String getGap() {
        return gap;
    }
    
    // Setters
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    public void setTeam(String team) {
        this.team = team;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public void setGap(String gap) {
        this.gap = gap;
    }
    
    @Override
    public String toString() {
        return "Position: " + position + " | " + driverName + " (" + team + ") | Time: " + time + " | Gap: " + gap;
    }
}
