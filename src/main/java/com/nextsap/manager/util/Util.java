package com.nextsap.manager.util;

import com.nextsap.manager.constant.ColorConstant;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Util {

    private static final Util INSTANCE = new Util();

    private Util() {
    }

    public boolean openLink(URI link) {
        try {
            Desktop.getDesktop().browse(link);
            print("${green}SUCCES: Le lien s'est ouvert dans le navigateur${reset}");
            return true;
        } catch (Exception exception) {
            print("${red}ERREUR: Une erreur est survenue lors de l'exécution de la commande${reset} " + exception.getMessage());
            return false;
        }
    }

    public boolean isDockerRunning() {
        try {
            Process process = new ProcessBuilder("/bin/bash", "-c", "docker info").start();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            boolean isRunning = !errorReader.lines().collect(Collectors.joining()).contains("Cannot connect to the Docker daemon");

            if (!isRunning)
                print("${red}ERREUR: Docker n'est pas lancé sur votre machine${reset}");


            return isRunning;
        } catch (Exception exception) {
            print("${red}ERREUR: Une erreur est survenue lors de l'exécution de la commande${reset} " + exception.getMessage());
            return false;
        }
    }

    public boolean exec(String command, boolean withOutput, long waitTime) {
        try {
            Thread.sleep(waitTime);
            Process process = new ProcessBuilder("/bin/bash", "-c", command).start();

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            inputReader.lines().forEach(this::print);
            errorReader.lines().forEach(this::print);

            if (withOutput) print("${green}SUCCES: Commande réalisée avec succès${reset}");

            return true;
        } catch (Exception exception) {
            print("${red}ERREUR: Une erreur est survenue lors de l'exécution de la commande${reset} " + exception.getMessage());
            return false;
        }
    }

    public void print(String string) {
        System.out.println(ColorConstant.replace(string));
    }

    private Properties loadProperties() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
            Properties configuration = new Properties();
            configuration.load(inputStream);

            return configuration;
        } catch (Exception exception) {
            throw new RuntimeException("Une erreur est survenue lors du chargement du fichier de configuration", exception);
        }
    }

    public Map<String, String> fetchEnvironment() {
        Properties properties = loadProperties();

        Map<String, String> environment = new HashMap<>();
        properties.keySet().forEach(key -> environment.put(key.toString(), properties.getProperty(key.toString())));

        return environment;
    }

    public String getArgValue(String arg) {
        return arg.split("=")[1];
    }

    public static Util getInstance() {
        return INSTANCE;
    }
}
