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

        util.print("${blue}Bienvenue dans le manager de l'application ${red}POSEIDON${blue} (P7)${reset}");

        while (!exit) {
            util.print("${yellow}INFO: Veuillez saisir une option${reset} (Tapez 1 pour afficher les options disponibles)");
            InputReaderArgs inputReaderArgs = inputReaderUtil.readSelection();

            if (inputReaderArgs.isError()) {
                util.print("${red}ERREUR: Option invalide${reset} (Tapez 1 pour afficher les options disponibles)");
                continue;
            }

            int option = Integer.parseInt(inputReaderArgs.getArgs()[0]);
            switch (option) {
                case 0 -> {
                    command3();
                    util.print("${blue}Arrêt du manager${reset}");
                    exit = true;
                }
                case 1 -> command1();
                case 2 -> command2(inputReaderArgs.getArgs(), useWindows);
                case 3 -> command3();
                case 4 -> command4(environment);
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
                          ${green}2${reset}   Créer et lancer les containers Docker (Vault, MySQL, Spring Boot)
                          ${green}3${reset}   Arrêter et supprimer les containers Docker (Vault, MySQL, Spring Boot)
                          ${green}4${reset}   Afficher les variables d'environnement
                        """;

        util.print(print);
    }

    private void command2(String[] args, boolean useWindows) {
        String error = """
                ${reset}| ${red}ERREUR: Les arguments entrés sont invalides${reset}
                ${reset}| ${yellow}INFO: Secrets à initialiser :
                ${reset}|    ${yellow}- spring.datasource.username     ${red}Requis
                ${reset}|    ${yellow}- spring.datasource.password     ${red}Requis
                ${reset}|    
                ${reset}| ${yellow}INFO: Exemple d'utilisation:
                ${reset}|    ${yellow}2 spring.datasource.username=root spring.datasource.password=root
                ${reset}|                     
                ${reset}| ${yellow}INFO: Exemple d'utilisation pour lancer en mode ⭐️${purple}flemmard ${yellow}:
                ${reset}|    ${yellow}2 ignore
                """;
        if (args.length < 3 && args.length != 2) {
            util.print(error);
            return;
        }

        String sleepCommand = "sleep";
        String operator = ";";
        if (useWindows) {
            sleepCommand = "timeout /t";
            operator = "&";
        }

        if (args.length == 2 && args[1].equals("ignore")) {
            util.exec("docker-compose up -d --build " + operator + " docker-compose logs -f | grep -m 1 'Development mode should NOT be used in production installations!' " + operator + " docker exec poseidon-vault vault login 00000000-0000-0000-0000-000000000000 " + operator + " " + sleepCommand + " .5 " + operator + " docker exec poseidon-vault vault kv put secret/poseidon spring.datasource.username=root spring.datasource.password=root", true, 0);
            return;
        }

        if (args.length != 3) {
            util.print(error);
            return;
        }

        util.exec("docker-compose up -d --build " + operator + " docker-compose logs -f | grep -m 1 'Development mode should NOT be used in production installations!' " + operator + " docker exec poseidon-vault vault login 00000000-0000-0000-0000-000000000000 " + operator + " " + sleepCommand + " .5 " + operator + " docker exec poseidon-vault vault kv put secret/poseidon " + args[1] + " " + args[2], true, 0);
    }

    private void command3() {
        util.exec("docker-compose down", true, 0);
    }

    private void command4(Map<String, String> environment) {
        util.print("${yellow}INFO: Variables d'environnement configurées:${reset}");
        environment.forEach((key, value) -> util.print("  ${green}" + key + "${reset} = ${green}" + value));
    }
}