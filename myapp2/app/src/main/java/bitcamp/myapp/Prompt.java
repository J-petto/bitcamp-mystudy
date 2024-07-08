package bitcamp.myapp;

import java.util.Scanner;

public class Prompt {
    static Scanner scanner = new Scanner(System.in);

    public static String input(String format, Object... args){
        System.out.printf(format + " ", args);
        return scanner.nextLine();
    }
}
