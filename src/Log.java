import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Log {


    private enum ActionType {
        INIT,
        FORWARD,
        BACKWARD,
        UPDATE;

        @Override
        public String toString() {
            switch (this) {
                case INIT:
                    return "INIT";
                case FORWARD:
                    return "REDO";
                case BACKWARD:
                    return "UNDO";
                case UPDATE:
                    return "UPDATE";
                default:
                    return "";
            }
        }
    }



    private class LogEntry {
        String text;
        LocalTime time;
        ActionType actionType;


        public LogEntry(String text, LocalTime time, ActionType actionType) {
            this.text = text;
            this.time = time;
            this.actionType = actionType;
        }

        public void saveLog() {
            try {
                Files.write(Paths.get("log.log"), this.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " " +
                    actionType.toString() + ": " +
                    text + "\n";
        }
    }

    private List<LogEntry> entries;
    private int entryCount;
    private int entryIndex;





    public Log() {
        entries = new ArrayList<>();
        entries.add(new LogEntry("", LocalTime.now(), ActionType.INIT));
        entryCount = 1;
        entryIndex = 0;

        try {
            Files.deleteIfExists(Paths.get("log.log"));
            Files.createFile(Paths.get("log.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLatestState() {
        System.err.println("TIMER: index: " + entryIndex + " count: " + entryCount);
        if (entryIndex >= 0)
            return entries.get(entryIndex).text;
        else
            return "";
    }

    public String undoStatus() {
        System.err.println("UNDO: index: " + entryIndex + " count: " + entryCount);
        if (entryIndex > 0) {
            entryIndex--;

            entries.get(entryIndex).actionType = ActionType.BACKWARD;
            entries.get(entryIndex).time = LocalTime.now();
            entries.get(entryIndex).saveLog();

            return entries.get(entryIndex).text;
        }
        else {
            entryIndex = 0;
            return "";
        }
    }

    public String redoStatus() {
        System.err.println("REDO: index: " + entryIndex + " count: " + entryCount);
        if (entryIndex < entryCount - 1) {
            entryIndex++;

            entries.get(entryIndex).actionType = ActionType.FORWARD;
            entries.get(entryIndex).time = LocalTime.now();
            entries.get(entryIndex).saveLog();

            return entries.get(entryIndex).text;
        }
        else
            return entries.get(entryIndex).text;

    }

    public void updateStatus(String text) {
        System.err.println("UPDATE: index: " + entryIndex + " count: " + entryCount);

        if (entryIndex < entryCount - 1 || entryIndex < entries.size() - 1) {
            entries.set(entryIndex + 1, new LogEntry(text, LocalTime.now(), ActionType.UPDATE));
        }

        else
            entries.add(new LogEntry(text, LocalTime.now(), ActionType.UPDATE));

        entryIndex++;
        entries.get(entryIndex).saveLog();

        entryCount = entryIndex + 1;
    }



}
