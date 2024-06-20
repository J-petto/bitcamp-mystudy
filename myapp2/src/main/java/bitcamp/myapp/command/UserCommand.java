package bitcamp.myapp.command;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.User;

public class UserCommand {
    private static final int USER_MAX = 50;
    private static User[] users = new User[USER_MAX];
    private static int userLength = 0;

    public static void autoUser() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            int id = i + 1;
            user.setName(String.valueOf(id));
            user.setEmail(String.format("%d@%d.com", id, id));
            user.setPassword(String.format("%d%d%d%d", id, id, id, id));
            user.setTel(String.format("%d%d%d", id, id, id));
            users[userLength++] = user;
        }
    }

    public static void userProcess(String subMenuTitle) {
        switch (subMenuTitle) {
            case "등록":
                createUser();
                break;
            case "목록":
                listUser();
                break;
            case "조회":
                searchUser();
                break;
            case "변경":
                updateUser();
                break;
            case "삭제":
                deleteUser();
                break;
            default:
        }
    }

    private static void createUser() {
        User user = new User();
        user.setName(Prompt.prompt("이름?"));
        user.setEmail(Prompt.prompt("이메일?"));
        user.setPassword(Prompt.prompt("비밀번호?"));
        user.setTel(Prompt.prompt("연락처?"));
        users[userLength++] = user;
        System.out.printf("%s님 등록했습니다.\n", user.getName());
    }

    private static void listUser() {
        System.out.println("번호 이름 이메일");
        for (int i = 0; i < userLength; i++) {
            User user = users[i];
            System.out.printf("%d. %s %s\n", (i + 1), user.getName(), user.getEmail());
        }
    }

    private static void searchUser() {
        while (true) {
            try {
                int userNo = Prompt.promptInt("회원번호?");
                User user = getUserByNo(userNo);
                if (user == null) {
                    System.out.println("없는 회원입니다.");
                    break;
                }
                System.out.printf("이름: %s\n", user.getName());
                System.out.printf("이메일: %s\n", user.getEmail());
                System.out.printf("암호: %s\n", user.getPassword());
                System.out.printf("연락처: %s\n", user.getTel());
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요.");
            }
        }
    }

    private static void updateUser() {
        while (true) {
            try {
                int userNo = Prompt.promptInt("회원번호?");
                User user = getUserByNo(userNo);
                if (user == null) {
                    System.out.println("없는 회원입니다.");
                    break;
                }
                user.setName(Prompt.prompt("이름(%s)?", user.getName()));
                user.setEmail(Prompt.prompt("이메일(%s)?", user.getEmail()));
                user.setPassword(Prompt.prompt("암호?"));
                user.setTel(Prompt.prompt("연락처(%s)?", user.getTel()));
                System.out.println("변경하였습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요.");
            }
        }
    }

    private static void deleteUser() {
        while (true) {
            try {
                int userNo = Prompt.promptInt("회원번호?");
                User user = getUserByNo(userNo);
                if (user == null) {
                    System.out.println("없는 회원입니다.");
                    break;
                }
                for (int i = userNo; i < userLength + 1; i++){
                    users[i - 1] = users[i];
                }
                users[--userLength] = null;
                System.out.println("삭제하였습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요.");
            }
        }
    }

    public static User getUserByNo(int userNo) {
        if (userNo < 1 && userNo > userLength) {
            return null;
        }
        return users[userNo - 1];
    }
}
