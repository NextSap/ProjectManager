package com.nextsap.manager.util;

import java.util.Scanner;

public class InputReaderUtil {

    private static final Scanner SCAN = new Scanner(System.in);
    private static final InputReaderUtil INSTANCE = new InputReaderUtil();
    private InputReaderUtil() {

    }

    /**
     * Return the
     * @return number written to the console by the user
     */
    public int readSelection() {
        try {
            return Integer.parseInt(SCAN.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public static InputReaderUtil getInstance() {
        return INSTANCE;
    }
}
