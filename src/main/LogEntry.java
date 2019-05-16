package main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class LogEntry {
    String text;
    LocalTime time;
    ActionType actionType;

    LogEntry(String text, LocalTime time, ActionType actionType) {
        this.text = text;
        this.time = time;
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " " +
                actionType.toString() + ": " +
                text + "\n";
    }
}