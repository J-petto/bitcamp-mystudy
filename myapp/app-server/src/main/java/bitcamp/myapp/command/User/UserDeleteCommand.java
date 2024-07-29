package bitcamp.myapp.command.User;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.MapUserDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.util.List;
import java.util.Map;

public class UserDeleteCommand implements Command {

    private UserDao userDao;

    public UserDeleteCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(String menuName) {
        try {
            System.out.printf("[%s]\n", menuName);

            int userNo = Prompt.inputInt("회원번호?");

            User deletedUser = userDao.findBy(userNo);
            if (deletedUser == null) {
                System.out.println("없는 회원입니다.");
                return;
            }
            userDao.delete(userNo);
            System.out.printf("'%s' 회원을 삭제 했습니다.\n", deletedUser.getName());

        } catch (Exception e) {
            System.out.println("유저 데이터 삭제 중 오류 발생");
        }
    }
}