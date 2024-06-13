package bitcamp.myapp;

import java.util.Scanner;

public class App {

    static Scanner keyboardScanner = new Scanner(System.in);

    static String[] mainMenus = new String[]{"회원", "팀", "프로젝트", "게시판", "도움말", "종료"};
    static String[][] subMenus = {
            {"등록", "목록", "조회", "변경", "삭제"},
            {"등록", "목록", "조회", "변경", "삭제"},
            {"등록", "목록", "조회", "변경", "삭제"},
            {"등록", "목록", "조회", "변경", "삭제"},
    };

    public static void main(String[] args) {

        User user1 = new User("홍길동", "hong@test.com", "1111", "010-1111-1111");
        User user2 = new User("임꺽정", "leem@test.com", "2222", "010-2222-2222");
        User user3 = new User("유관순", "yoo@test.com", "3333", "010-3333-3333");
        User.createUser(user1);
        User.createUser(user2);
        User.createUser(user3);

        printMenu(); // 메서드에 묶인 코드를 실행하는 것을 "메서드를 호출(call)한다"라고 부른다.

        String command;
        while (true) {
            try {
                command = prompt("메인");
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
                        if (menuNo > 0 && menuNo <= 4) {
                            subProcess(subMenus[menuNo - 1], menuTitle);
                        } else {
                            System.out.println(menuTitle);
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
            }
        }

        System.out.println("종료합니다.");

        keyboardScanner.close();
    }

    static void printMenu() {
        String boldAnsi = "\033[1m";
        String redAnsi = "\033[31m";
        String resetAnsi = "\033[0m";

        String appTitle = "[팀 프로젝트 관리 시스템]";
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

    static void printSubMenu(String[] menus, String title) {
        System.out.printf("[%s]\n", title);
        for (int i = 0; i < menus.length; i++) {
            System.out.printf("%d. %s \n", i + 1, menus[i]);
        }
        System.out.println("9. 이전");
    }

    static String prompt(String title) {
        System.out.printf("%s> ", title);
        return keyboardScanner.nextLine();
    }

    static boolean isValidateMenu(int menuNo, String[] menus) {
        return menuNo >= 1 && menuNo <= menus.length;
    }

    static String getMenuTitle(int menuNo, String[] menus) {
//        if (isValidateMenu(menuNo)) {
//            return menus[menuNo - 1];
//        }
//        return null;

        return isValidateMenu(menuNo, menus) ? menus[menuNo - 1] : null;
    }

    static void subProcess(String[] menus, String menuTitle) {
        printSubMenu(menus, menuTitle);
        while (true) {
            String command = prompt("메인/" + menuTitle);
            if (command.equals("menu")) {
                printSubMenu(menus, menuTitle);
            } else if (command.equals("9")) {
                break;
            }
            try {
                int subMenuNo = Integer.parseInt(command);
                String subMenuTitle = getMenuTitle(subMenuNo, menus);

                if (subMenuTitle == null) {
                    System.out.println("유효한 메뉴 번호가 아닙니다.");
                } else {
                    System.out.printf("[%s]\n", subMenuTitle);
                    switch (subMenuNo) {
                        case 1:
                            createUser();
                            break;
                        case 2:
                            printUserList();
                            break;
                        case 3:
                            searchUser();
                            break;
                        case 4:
                            updateUser();
                            break;
                        case 5:
                            deleteUser();
                            break;
                        default:
                            System.out.println(subMenuTitle);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
            }
        }
    }

    static void createUser() {
        System.out.print("이름? ");
        String name = keyboardScanner.nextLine();
        System.out.print("이메일? ");
        String email = keyboardScanner.nextLine();
        System.out.print("암호? ");
        String password = keyboardScanner.nextLine();
        System.out.print("연락처? ");
        String tel = keyboardScanner.nextLine();

        User user = new User(name, email, password, tel);
        User.createUser(user);
    }

    static void printUserList() {
        System.out.println("번호 이름 이메일");
        int i = 1;
        for (User user : User.getUsers()) {
            System.out.printf("%d %s %s\n", i, user.getName(), user.getEmail());
            i++;
        }
    }

    static void searchUser() {
        while (true) {
            System.out.print("회원번호? ");
            try {
                String checkUserNo = keyboardScanner.nextLine();
                int userNo = Integer.parseInt(checkUserNo);
                User foundUser = User.getUserByIndex(userNo - 1);
                if (foundUser != null) {
                    System.out.println("이름 : " + foundUser.getName());
                    System.out.println("이메일 : " + foundUser.getEmail());
                    System.out.println("연락처 : " + foundUser.getTell());
                    break;
                } else {
                    System.out.println("없는 회원입니다.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("회원 번호를 입력해주세요.");
            }
        }
    }

    static void updateUser() {
        while (true) {
            System.out.print("회원번호? ");
            try {
                String checkUserNo = keyboardScanner.nextLine();
                int userNo = Integer.parseInt(checkUserNo);
                User foundUser = User.getUserByIndex(userNo - 1);
                if (foundUser != null) {
                    System.out.printf("이름(%s)? ", foundUser.getName());
                    foundUser.setName(keyboardScanner.nextLine());

                    System.out.printf("이메일(%s)? ", foundUser.getEmail());
                    foundUser.setEmail(keyboardScanner.nextLine());

                    System.out.print("암호? ");
                    foundUser.setPassword(keyboardScanner.nextLine());

                    System.out.printf("연락처(%s)? ", foundUser.getTell());
                    foundUser.setTell(keyboardScanner.nextLine());

                    System.out.println("변경하였습니다.");
                    break;
                } else {
                    System.out.println("없는 회원입니다.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    static void deleteUser() {
        while (true) {
            System.out.print("회원번호? ");
            try {
                String checkUserNo = keyboardScanner.nextLine();
                int userNo = Integer.parseInt(checkUserNo);
                User foundUser = User.getUserByIndex(userNo - 1);
                if (foundUser != null) {
                    User.deleteUser(userNo);
                    System.out.println("삭제하였습니다.");
                    break;
                } else {
                    System.out.println("없는 회원입니다.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }
}
