package com.nextsap.manager;

import java.util.Arrays;
import java.util.Map;

public class Manager {

    public static void main(String[] args) {
        String arg = Arrays.toString(args).replace("[","").replace("]","");
        String port = arg.contains("SERVICE_PORT=") ? arg.split("SERVICE_PORT=")[1].split(" ")[0] : "8080";

        Map<String,String> environment = Map.of(
                "SERVICE_PORT", port
        );

        InteractiveShell.loadInterface(environment);
    }
}
