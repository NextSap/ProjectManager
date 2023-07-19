package com.nextsap.manager.util;

import com.nextsap.manager.object.InputReaderArgs;

import java.util.Scanner;

public class InputReaderUtil {

    private static final Scanner SCAN = new Scanner(System.in);
    private static final InputReaderUtil INSTANCE = new InputReaderUtil();

    private InputReaderUtil() {

    }

    /**
     * Read the user input
     * @return InputReaderArgs
     */
    public InputReaderArgs readSelection() {
        InputReaderArgs inputReaderArgs = new InputReaderArgs();
        try {
            String input = SCAN.nextLine();
            Integer.parseInt(input.split(" ")[0]);
            inputReaderArgs.setArgs(input.split(" "));
        } catch (Exception e) {
            inputReaderArgs.setError(true);
        }
        return inputReaderArgs;
    }

    public static InputReaderUtil getInstance() {
        return INSTANCE;
    }
}
