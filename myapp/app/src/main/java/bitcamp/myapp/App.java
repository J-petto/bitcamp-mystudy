/*
 * This source file was generated by the Gradle 'init' task
 */
package bitcamp.myapp;

import java.util.Scanner;

public class App {
    static Scanner keboardScanner = new Scanner(System.in);

    static String[] menus = {
            "회원",
            "팀",
            "프로젝트",
            "게시판",
            "도움말",
            "종료"
    };

    public static void main(String[] args) {
        printMenu();

        String commend;
        int menuNo;
        while (true) {
            try {
                commend = prompt();
                if (commend.equals("menu")) {
                    printMenu(); //메서드를 실행함 = 메서드 호출(call)함
                }

                menuNo = Integer.parseInt(commend);
                String menuTitle = getMenuTitle(menuNo); // 설명하는 변수
                if(menuTitle == null){
                    System.out.println("메뉴 번호가 아닙니다.");
                }else if (menuTitle.equals("종료")){
                    System.out.println("종료합니다.");
                    break;
                }else {
                    System.out.println(getMenuTitle(menuNo));
                }

            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }

        // 사용을 완료한 자원은 반환해야 사용가능
        // JVM을 종료하면 JVM이 사용한 모든 자원은 강제 회수
        // OS 강제 회수
        keboardScanner.close();
    }

    static void printMenu() {
        String appTitle = "[팀 프로젝트 관리 시스템]";
        String dashLine = "--------------------------------------------";
        String boldANSI = "\033[1m";
        String redColorANSI = "\033[31m";
        String endANSI = "\033[0m";

        System.out.println(boldANSI + dashLine + endANSI);
        System.out.println(boldANSI + appTitle + endANSI + "\n");

        for (int i = 0; i < menus.length; i++) {
            if (menus[i].equals("종료")) {
                System.out.printf("%s%d. %s%s \n", (boldANSI + redColorANSI), (i + 1), menus[i], endANSI);
            } else {
                System.out.printf("%d. %s \n", (i + 1), menus[i]);
            }
        }
        System.out.println(boldANSI + dashLine + endANSI);
    }

    static String prompt() {
        System.out.print("> ");
        return keboardScanner.nextLine();
    }

    static boolean isValidateMenu(int menuNo) {
        return menuNo >= 1 && menuNo <= menus.length;
    }

    static String getMenuTitle(int menuNo) {
//        if (isValidateMenu(menuNo)) {
//            return menus[menuNo - 1];
//        }
//        return null;
        return isValidateMenu(menuNo) ? menus[menuNo - 1] : null;
    }

}
