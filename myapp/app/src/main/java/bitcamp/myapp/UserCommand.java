package bitcamp.myapp;

public class UserCommand {
    static final int MAX_LENGTH = 50;
    static User[] users = new User[MAX_LENGTH];
    static int userLength = 0;

    static void autoCreateUser() {
        for(int i = 0; i < 2; i++){
            User user = new User();
            user.name = String.valueOf(i+1);
            user.email = String.format("%d@%d.com", i+1, i+1);
            user.password = String.format("%d%d%d%d",i+1, i+1, i+1, i+1);
            user.tel = String.format("010-1111-111%d", i+1);
            users[userLength++] = user;
        }
    }

    static void createUser() {
        User user = new User();
        user.name = Prompt.prompt("이름? ");
        user.email = Prompt.prompt("이메일? ");
        user.password = Prompt.prompt("암호? ");
        user.tel = Prompt.prompt("연락처? ");
        users[userLength++] = user;
    }

    static void listUser() {
        System.out.println("번호 이름 이메일");
        for (int i = 0; i < userLength; i++) {
            User user = users[i];
            System.out.printf("%d %s %s\n", i + 1, user.name, user.email);
        }
    }

    static void searchUser() {
        User user;
        while (true) {
            try {
                int userNo = Integer.parseInt(Prompt.prompt("회원번호? "));
                if (userNo > userLength) {
                    System.out.println("없는 회원입니다.");
                    break;
                }
                user = users[userNo - 1];
                System.out.printf("이름 : %s\n", user.name);
                System.out.printf("이메일 : %s\n", user.email);
                System.out.printf("연락처 : %s\n", user.tel);
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    static void updateUser() {
        User user;
        while (true) {
            try {
                int userNo = Integer.parseInt(Prompt.prompt("회원번호? "));
                if (userNo > userLength) {
                    System.out.println("없는 회원입니다.");
                    break;
                }
                user = users[userNo - 1];
                user.name = Prompt.prompt(String.format("이름(%s)? ", user.name));
                user.email = Prompt.prompt(String.format("이메일(%s)? ", user.email));
                user.password = Prompt.prompt("암호? ");
                user.tel = Prompt.prompt(String.format("연락처(%s)? ", user.tel));
                System.out.println("변경 했습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    static void deleteUser() {
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
