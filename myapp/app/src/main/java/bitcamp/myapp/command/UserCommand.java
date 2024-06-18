package bitcamp.myapp.command;

import bitcamp.myapp.vo.User;
import bitcamp.myapp.util.Prompt;

public class UserCommand {
    static final int MAX_LENGTH = 50;
    static User[] users = new User[MAX_LENGTH];
    static int userLength = 0;

    public static void autoCreateUser() {
        for(int i = 0; i < 5; i++){
            User user = new User();
            user.setName(String.valueOf(i+1));
            user.setEmail(String.format("%d@%d.com", i+1, i+1));
            user.setPassword(String.format("%d%d%d%d",i+1, i+1, i+1, i+1));
            user.setTel(String.format("010-1111-111%d", i+1));
            users[userLength++] = user;
        }
    }

    public static void executeUserCommand(String subMenuTitle) {
        switch (subMenuTitle) {
            case "등록":
                UserCommand.addUser();
                break;
            case "목록":
                UserCommand.listUser();
                break;
            case "조회":
                UserCommand.searchUser();
                break;
            case "변경":
                UserCommand.updateUser();
                break;
            case "삭제":
                UserCommand.deleteUser();
                break;
        }
    }

    private static void addUser() {
        User user = new User();
        user.setName(Prompt.prompt("이름? "));
        user.setEmail(Prompt.prompt("이메일? "));
        user.setPassword(Prompt.prompt("암호? "));
        user.setTel(Prompt.prompt("연락처? "));
        users[userLength++] = user;
    }

    private static void listUser() {
        System.out.println("번호 이름 이메일");
        for (int i = 0; i < userLength; i++) {
            User user = users[i];
            System.out.printf("%d %s %s\n", i + 1, user.getName(), user.getEmail());
        }
    }

    private static void searchUser() {
        User user;
        while (true) {
            try {
                int userNo = Integer.parseInt(Prompt.prompt("회원번호? "));
                if (userNo > userLength) {
                    System.out.println("없는 회원입니다.");
                    break;
                }
                user = users[userNo - 1];
                System.out.printf("이름 : %s\n", user.getName());
                System.out.printf("이메일 : %s\n", user.getEmail());
                System.out.printf("연락처 : %s\n", user.getTel());
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private static void updateUser() {
        User user;
        while (true) {
            try {
                int userNo = Integer.parseInt(Prompt.prompt("회원번호? "));
                if (userNo > userLength) {
                    System.out.println("없는 회원입니다.");
                    break;
                }
                user = users[userNo - 1];
                user.setName(Prompt.prompt(String.format("이름(%s)? ", user.getName())));
                user.setEmail(Prompt.prompt(String.format("이메일(%s)? ", user.getEmail())));
                user.setPassword(Prompt.prompt("암호? "));
                user.setPassword(Prompt.prompt(String.format("연락처(%s)? ", user.getPassword())));
                System.out.println("변경 했습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private static void deleteUser() {
        while (true) {
            try {
                int userNo = Integer.parseInt(Prompt.prompt("회원번호? "));
                if (userNo > userLength) {
                    System.out.println("없는 회원입니다.");
                    break;
                }
                users[userNo - 1] = users[userNo];
                users[userNo] = null;
                userLength--;
                System.out.println("삭제 했습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

}
