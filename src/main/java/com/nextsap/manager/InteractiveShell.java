package com.nextsap.manager;

import com.nextsap.manager.util.InputReaderUtil;
import com.nextsap.manager.util.Util;

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
                    util.print("${blue}Arrêt du manager${reset}");
                    exit = true;
                }
                case 1 -> command1();
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
                        """;

        util.print(print);
    }
}