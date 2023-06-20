package com.nextsap.manager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Manager {

    public static void main(String[] args) {
        Properties properties = load();
        Map<String, String> environment = new HashMap<>();
        properties.keySet().forEach(key -> environment.put(key.toString(), properties.getProperty(key.toString())));

        InteractiveShell.loadInterface(environment);
    }

    private static Properties load() {
        try {
            Properties configuration = new Properties();
            InputStream inputStream = new FileInputStream("src/main/resources/application.properties");
            configuration.load(inputStream);
            inputStream.close();
            return configuration;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
