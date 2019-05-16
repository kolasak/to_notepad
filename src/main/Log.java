package main;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class Log {
    private List<LogEntry> entries;
    private int entryCount;
    private int entryIndex;
    private FileHandler fileHandler;

    Log() {
        fileHandler = new FileHandler();
        entries = new ArrayList<>();
        entries.add(new LogEntry("", LocalTime.now(), ActionType.INIT));
        entryCount = 1;
        entryIndex = 0;
    }

    String getLatestState() {
        System.err.println("TIMER: index: " + entryIndex + " count: " + entryCount);
        if (entryIndex >= 0)
            return entries.get(entryIndex).text;
        else
            return "";
    }

    String undoStatus() {
        System.err.println("UNDO: index: " + entryIndex + " count: " + entryCount);
        if (entryIndex > 0) {
            entryIndex--;

            LogEntry logEntry = entries.get(entryIndex);
            logEntry.actionType = ActionType.FORWARD;
            logEntry.time = LocalTime.now();
            fileHandler.saveLog(logEntry);
            return logEntry.text;
        } else {
            entryIndex = 0;
            return "";
        }
    }

    String redoStatus() {
        System.err.println("REDO: index: " + entryIndex + " count: " + entryCount);
        if (entryIndex < entryCount - 1) {
            entryIndex++;
            LogEntry logEntry = entries.get(entryIndex);
            logEntry.actionType = ActionType.FORWARD;
            logEntry.time = LocalTime.now();
            fileHandler.saveLog(logEntry);
        }
        return entries.get(entryIndex).text;
    }

    void updateStatus(String text) {
        System.err.println("UPDATE: index: " + entryIndex + " count: " + entryCount);

        if (entryIndex < entryCount - 1 || entryIndex < entries.size() - 1) {
            entries.set(entryIndex + 1, new LogEntry(text, LocalTime.now(), ActionType.UPDATE));
        } else
            entries.add(new LogEntry(text, LocalTime.now(), ActionType.UPDATE));

        entryIndex++;
        fileHandler.saveLog(entries.get(entryIndex));

        entryCount = entryIndex + 1;
    }


}
