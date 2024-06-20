package bitcamp.myapp.util;

import java.util.Scanner;

public class Prompt {
    static Scanner keyboardScanner = new Scanner(System.in);

    public static String prompt(String format, Object... args) {
        System.out.printf(format + " ", args);
        return keyboardScanner.nextLine();
    }

    public static int promptInt(String title){
        return Integer.parseInt(prompt(title));
    }

    public static void close() {
        keyboardScanner.close();
    }
}
