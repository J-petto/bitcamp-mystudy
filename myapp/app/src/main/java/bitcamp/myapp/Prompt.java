package bitcamp.myapp;

import java.util.Scanner;

public class Prompt {
    static Scanner scanner = new Scanner(System.in);

    static String prompt(String comment) {
        System.out.print(comment);
        return scanner.nextLine();
    }

    static void closeScanner(){
        scanner.close();
    }
}
