package bitcamp.myapp;

import bitcamp.myapp.command.*;
import bitcamp.myapp.util.*;

import java.util.HashMap;
import java.util.Map;

public class App {
    String[] mainMenus = new String[]{"회원", "프로젝트", "게시판", "도움말", "명령 내역", "종료"};

    Stack menuPath = new Stack();

    Map<String, Command> commandMap = new HashMap<>();

    public App(){
        List userList = new ArrayList();
        List projectList = new LinkedList();
        List boardList = new LinkedList();

        UserCommand userCommand= new UserCommand("회원", userList);
        commandMap.put("회원", userCommand);
        commandMap.put("게시판", new BoardCommand("게시판", boardList));
        commandMap.put("프로젝트", new ProjectCommand("프로젝트", projectList, userList));
        commandMap.put("도움말", new HelpCommand());
        commandMap.put("명령 내역", new HistoryCommand());
    }

    public static void main(String[] args) {
        new App().execute();
    }

    void execute() {
        menuPath.push("메인");

        printMenu();

        String command;
        while (true) {
            try {
                command = Prompt.input("%s>", getMenuPathTitle(menuPath));

                if (command.equals("menu")) {
                    printMenu();

                } else {
                    int menuNo = Integer.parseInt(command);
                    String menuTitle = getMenuTitle(menuNo, mainMenus); // 설명하는 변수
                    if (menuTitle == null) {
                        System.out.println("유효한 메뉴 번호가 아닙니다.");
                    } else if (menuTitle.equals("종료")) {
                        break;
                    } else {
                        processMenu(menuTitle);
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
            }
        }

        System.out.println("종료합니다.");

        Prompt.close();
    }

    void printMenu() {
        String boldAnsi = "\033[1m";
        String redAnsi = "\033[31m";
        String resetAnsi = "\033[0m";

        String appTitle = "[프로젝트 관리 시스템]";
        String line = "----------------------------------";

        System.out.println(boldAnsi + line + resetAnsi);
        System.out.println(boldAnsi + appTitle + resetAnsi);

        for (int i = 0; i < mainMenus.length; i++) {
            if (mainMenus[i].equals("종료")) {
                System.out.printf("%s%d. %s%s\n", (boldAnsi + redAnsi), (i + 1), mainMenus[i], resetAnsi);
            } else {
                System.out.printf("%d. %s\n", (i + 1), mainMenus[i]);
            }
        }

        System.out.println(boldAnsi + line + resetAnsi);
    }

    boolean isValidateMenu(int menuNo, String[] menus) {
        return menuNo >= 1 && menuNo <= menus.length;
    }

    String getMenuTitle(int menuNo, String[] menus) {
        return isValidateMenu(menuNo, menus) ? menus[menuNo - 1] : null;
    }

    void processMenu(String menuTitle) {
        Command command = commandMap.get(menuTitle);
        if(command == null){
            System.out.printf("%s 메뉴의 명령을 처리할 수 없습니다.\n", menuTitle);
            return;
        }
        command.execute(menuPath);
    }

    private String getMenuPathTitle(Stack menuPath){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < menuPath.size(); i++){
            if(!stringBuilder.isEmpty()){
                stringBuilder.append("/");
            }
            stringBuilder.append(menuPath.get(i));
        }
        return stringBuilder.toString();
    }

}
