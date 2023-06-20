package com.nextsap.manager;

import com.nextsap.manager.constant.ColorConstant;
import com.nextsap.manager.util.InputReaderUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

public class InteractiveShell {

    public static void loadInterface(Map<String, String> environment) {
        InputReaderUtil inputReaderUtil = InputReaderUtil.getInstance();
        boolean exit = false;

        print("${blue}Bienvenue dans le manager de l'application${reset}");

        while (!exit) {
            print("${yellow}INFO: Veuillez saisir une option${reset} (Tapez 1 pour afficher les options disponibles)");
            int option = inputReaderUtil.readSelection();
            switch (option) {
                case 0 -> {
                    command3();
                    print("${blue}Arrêt du manager${reset}");
                    exit = true;
                }
                case 1 -> command1();
                case 2 -> command2();
                case 3 -> command3();
                case 4 -> command4(environment.get("SERVICE_PORT"));
                case 5 -> command5(environment);
                default ->
                        print("${red}ERREUR: Option invalide${reset} (Tapez 1 pour afficher les options disponibles)");
            }
        }
    }


    private static void command1() {
        String print =
                """
                        ${yellow}Options disponibles:
                          ${green}0${reset}   Quitter le manager
                          ${green}1${reset}   Afficher les options disponibles
                          ${green}2${reset}   Créer et lancer les containers Docker de l'application
                          ${green}3${reset}   Stopper et supprimer les containers Docker de l'application
                          ${green}4${reset}   Lancer la documentation Swagger
                          ${green}5${reset}   Afficher les variables d'environnement
                        """;

        print(print);
    }

    private static void command2() {
        if(exec("docker-compose up -d --build"))
            print("${green}SUCCES: Les containers ont été créés${reset}");
    }

    private static void command3() {
        if(exec("docker-compose down"))
            print("${green}SUCCES: Les containers ont été supprimés${reset}");
    }

    private static void command4(String port) {
        try {
            Desktop.getDesktop().browse(URI.create("http://localhost:"+port+"/docs"));
            print("${green}SUCCES: La documentation s'est ouverte dans le navigateur${reset}");
        } catch (Exception exception) {
            print("${red}ERREUR: Une erreur est survenue lors de l'ouverture de la documentation Swagger${reset}");
        }
    }

    private static void command5(Map<String,String> environment) {
        print("${yellow}INFO: Variables d'environnement configurées:${reset}");
        environment.forEach((key, value) -> print("  ${green}" + key + "${reset} = ${green}" + value));
    }

    private static boolean exec(String command) {
        try {
            Process process = new ProcessBuilder("/bin/bash", "-c", command).start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            reader.lines().forEach(InteractiveShell::print);
            return true;
        } catch (Exception exception) {
            print("${red}ERREUR: Une erreur est survenue lors de l'exécution de la commande${reset} " + exception.getMessage());
            return false;
        }
    }

    private static void print(String string) {
        System.out.println(ColorConstant.replace(string));
    }
}
