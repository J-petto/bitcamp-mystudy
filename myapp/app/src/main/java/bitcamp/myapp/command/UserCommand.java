package bitcamp.myapp.command;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.User;

public class UserCommand {

    static public void autoAddUser() {
        for (int i = 1; i < 6; i++) {
            User u = new User();
            u.setName(String.valueOf(i));
            u.setEmail(String.format("%s@test.com", i));
            u.setPassword(String.valueOf(i));
            u.setTel(String.format("010-%d%d%d%d-%d%d%d%d", i, i, i, i, i, i, i, i));
            u.setNo(User.getNextSeqNo());
            UserList.add(u);
        }
    }

    public static void executeUserCommand(String command) {
        System.out.printf("[%s]\n", command);
        switch (command) {
            case "등록":
                addUser();
                break;
            case "조회":
                viewUser();
                break;
            case "목록":
                listUser();
                break;
            case "변경":
                updateUser();
                break;
            case "삭제":
                deleteUser();
                break;
        }
    }

    private static void addUser() {
        User user = new User();
        user.setName(Prompt.input("이름?"));
        user.setEmail(Prompt.input("이메일?"));
        user.setPassword(Prompt.input("암호?"));
        user.setTel(Prompt.input("연락처?"));
        user.setNo(User.getNextSeqNo());
        UserList.add(user);
    }



    private static void listUser() {
        System.out.println("번호 이름 이메일");
        for (User user : UserList.toArray()) {
            System.out.printf("%d %s %s\n", user.getNo(), user.getName(), user.getEmail());
        }
    }

    private static void viewUser() {
        int userNo = Prompt.inputInt("회원번호?");
        User user = UserList.findByNo(userNo);
        if (user == null) {
            System.out.println("없는 회원입니다.");
            return;
        }
        System.out.printf("이름: %s\n", user.getName());
        System.out.printf("이메일: %s\n", user.getEmail());
        System.out.printf("연락처: %s\n", user.getTel());
    }

    private static void updateUser() {
        int userNo = Prompt.inputInt("회원번호?");
        User user = UserList.findByNo(userNo);
        if (user == null) {
            System.out.println("없는 회원입니다.");
            return;
        }
        user.setName(Prompt.input("이름(%s)?", user.getName()));
        user.setEmail(Prompt.input("이메일(%s)?", user.getEmail()));
        user.setPassword(Prompt.input("암호?"));
        user.setTel(Prompt.input("연락처(%s)?", user.getTel()));
        System.out.println("변경 했습니다.");
    }

    private static void deleteUser() {
        int userNo = Prompt.inputInt("회원번호?");
        User deleteduser = UserList.delete(userNo);
        if(deleteduser != null){
            System.out.printf("%s 회원을 삭제했습니다.\n", deleteduser.getName());
            return;
        }
        System.out.println("삭제 했습니다.");
    }

}
