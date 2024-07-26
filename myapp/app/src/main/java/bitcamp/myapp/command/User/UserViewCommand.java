package bitcamp.myapp.command.User;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.util.List;
import java.util.Map;

public class UserViewCommand implements Command {

    private UserDao userDao;

    public UserViewCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(String menuName) {
        try {
            System.out.printf("[%s]\n", menuName);

            int userNo = Prompt.inputInt("회원번호?");

            User user = userDao.findBy(userNo);

            if (user == null) {
                System.out.println("없는 회원입니다.");
                return;
            }

            System.out.printf("이름: %s\n", user.getName());
            System.out.printf("이메일: %s\n", user.getEmail());
            System.out.printf("연락처: %s\n", user.getTel());

        } catch (Exception e) {
            System.out.println("유저 조회 중 오류 발생");
        }
    }
}