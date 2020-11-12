package com.example.stopwatchrecorder;

public class Session {

    String sessionName;
    String sessionDescription;
    String sessionDate;
    String timeRecord;

    public Session(String sessionName, String sessionDescription, String sessionDate, String timeRecord) {
        this.sessionName = sessionName;
        this.sessionDescription = sessionDescription;
        this.sessionDate = sessionDate;
        this.timeRecord = timeRecord;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(String timeRecord) {
        this.timeRecord = timeRecord;
    }
}
