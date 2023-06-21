package com.nextsap.manager;

import com.nextsap.manager.util.Util;

public class Manager {

    public static void main(String[] args) {
        new InteractiveShell(Util.getInstance().fetchEnvironment());
    }
}
