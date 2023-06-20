package com.nextsap.manager.constant;

public enum ColorConstant {
    RESET("\033[0m", "${reset}"),
    BLACK("\033[0;30m", "${black}"),
    RED("\033[0;31m", "${red}"),
    GREEN("\033[0;32m", "${green}"),
    YELLOW("\033[0;33m", "${yellow}"),
    BLUE("\033[0;34m", "${blue}"),
    PURPLE("\033[0;35m", "${purple}"),
    CYAN("\033[0;36m", "${cyan}"),
    WHITE("\033[0;37m", "${white}");

    private final String color;
    private final String command;

    ColorConstant(String color, String command) {
        this.color = color;
        this.command = command;
    }

    public String getColor() {
        return color;
    }

    public String getCommand() {
        return command;
    }

    public static String replace(String string) {
        for (ColorConstant colorConstant : ColorConstant.values()) {
            string = string.replace(colorConstant.getCommand(), colorConstant.getColor());
        }
        return string;
    }
}
