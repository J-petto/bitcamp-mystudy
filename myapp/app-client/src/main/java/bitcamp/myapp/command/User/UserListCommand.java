package bitcamp.myapp.command.User;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import java.util.List;
import java.util.Map;

public class UserListCommand implements Command {

    private UserDao userDao;

    public UserListCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(String menuName) {
        try {
            System.out.printf("[%s]\n", menuName);

            System.out.println("번호 이름 이메일");

            for (User user : userDao.list()) {
                System.out.printf("%d %s %s\n", user.getNo(), user.getName(), user.getEmail());
            }
        } catch (Exception e) {
            System.out.println("유저 목록 조회 중 오류 발생");
        }

    }
}