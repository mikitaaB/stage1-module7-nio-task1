package com.epam.mjc.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FileReader {
    private static final Logger log = Logger.getLogger(FileReader.class.getName());

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();

        Path path = file.toPath();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            processFile(reader, profile);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return profile;
    }

    private void processFile(BufferedReader reader, Profile profile) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line, profile);
        }
    }

    private void parseLine(String line, Profile profile) {
        String[] keyValue = line.split(": ");
        if (keyValue.length == 2) {
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            setProfileField(key, value, profile);
        }
    }

    private void setProfileField(String key, String value, Profile profile) {
        switch (key) {
            case "Name":
                profile.setName(value);
                break;
            case "Age":
                profile.setAge(Integer.parseInt(value));
                break;
            case "Email":
                profile.setEmail(value);
                break;
            case "Phone":
                profile.setPhone(Long.parseLong(value));
                break;
            default:
                if (log.isLoggable(Level.WARNING)) {
                    log.log(Level.WARNING, String.format("Unknown key: %s", key));
                }
                break;
        }
    }
}