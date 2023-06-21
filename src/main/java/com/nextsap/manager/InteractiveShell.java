package com.nextsap.manager;

import com.nextsap.manager.util.InputReaderUtil;
import com.nextsap.manager.util.Util;

import java.io.File;
import java.net.URI;
import java.util.Map;

public class InteractiveShell {

    private final Util util;

    public InteractiveShell(Map<String, String> environment) {
        util = Util.getInstance();
        loadInterface(environment);
    }

    public void loadInterface(Map<String, String> environment) {
        InputReaderUtil inputReaderUtil = InputReaderUtil.getInstance();
        boolean exit = false;

        util.print("${blue}Bienvenue dans le manager de l'application${reset}");

        while (!exit) {
            util.print("${yellow}INFO: Veuillez saisir une option${reset} (Tapez 1 pour afficher les options disponibles)");
            int option = inputReaderUtil.readSelection();
            switch (option) {
                case 0 -> {
                    command3(false);
                    util.print("${blue}Arrêt du manager${reset}");
                    exit = true;
                }
                case 1 -> command1();
                case 2 -> command2();
                case 3 -> command3(true);
                case 4 -> command4(environment.get("server.port"));
                case 5 -> command5();
                case 6 -> command6();
                case 7 -> command7(environment);
                default ->
                        util.print("${red}ERREUR: Option invalide${reset} (Tapez 1 pour afficher les options disponibles)");
            }
        }
    }


    private void command1() {
        String print =
                """
                        ${yellow}Options disponibles:
                          ${green}0${reset}   Quitter le manager
                          ${green}1${reset}   Afficher les options disponibles
                          ${green}2${reset}   Créer et lancer les containers Docker de l'application
                          ${green}3${reset}   Stopper et supprimer les containers Docker de l'application
                          ${green}4${reset}   Générer et afficher la documentation Swagger
                          ${green}5${reset}   Lancer les tests unitaires
                          ${green}6${reset}   Générer et afficher le rapport Jacoco
                          ${green}7${reset}   Afficher les variables d'environnement
                        """;

        util.print(print);
    }

    private void command2() {
        if (!util.isDockerRunning()) return;

        util.exec("docker-compose up -d --build", true);
    }

    private void command3(boolean withOutput) {
        if (!util.isDockerRunning()) return;

        util.exec("docker-compose down", withOutput);
    }

    private void command4(String port) {
        if (!util.isDockerRunning()) return;

        util.openLink(URI.create("http://localhost:" + port + "/docs"));
    }

    private void command5() {
        if (!util.isDockerRunning()) return;

        util.exec("mvn clean package verify site jacoco:report", true);
    }

    private void command6() {
        if (!util.isDockerRunning()) return;

        util.openLink(new File("target/site/jacoco/index.html").toURI());
    }

    private void command7(Map<String, String> environment) {
        util.print("${yellow}INFO: Variables d'environnement configurées:${reset}");
        environment.forEach((key, value) -> util.print("  ${green}" + key + "${reset} = ${green}" + value));
    }
}