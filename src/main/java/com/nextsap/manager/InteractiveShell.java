package com.nextsap.manager;

import com.nextsap.manager.object.InputReaderArgs;
import com.nextsap.manager.util.InputReaderUtil;
import com.nextsap.manager.util.Util;

import java.util.Map;

public class InteractiveShell {

    private final Util util;
    private final boolean useWindows;

    public InteractiveShell(Map<String, String> environment, String useWindows) {
        util = Util.getInstance();
        this.useWindows = Boolean.parseBoolean(useWindows);
        loadInterface(environment);
    }

    public void loadInterface(Map<String, String> environment) {
        InputReaderUtil inputReaderUtil = InputReaderUtil.getInstance();
        boolean exit = false;

        util.print("${blue}Bienvenue dans le manager de l'application${reset}");

        while (!exit) {
            util.print("${yellow}INFO: Veuillez saisir une option${reset} (Tapez 1 pour afficher les options disponibles)");
            InputReaderArgs inputReaderArgs = inputReaderUtil.readSelection();

            if(inputReaderArgs.isError()) {
                util.print("${red}ERREUR: Option invalide${reset} (Tapez 1 pour afficher les options disponibles)");
                continue;
            }

            int option = Integer.parseInt(inputReaderArgs.getArgs()[0]);
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