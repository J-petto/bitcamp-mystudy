package bitcamp.myapp.util;

import java.util.Scanner;

public class Prompt {
    static Scanner scanner = new Scanner(System.in);

    public static String prompt(String comment) {
        System.out.print(comment);
        return scanner.nextLine();
    }

    public static void closeScanner(){
        scanner.close();
    }
}
