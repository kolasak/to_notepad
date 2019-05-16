package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class FileHandler {
    FileHandler() {
        try {
            Files.deleteIfExists(Paths.get("log.log"));
            Files.createFile(Paths.get("log.log"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    void saveLog(LogEntry log) {
        try {
            Files.write(Paths.get("log.log"), log.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
