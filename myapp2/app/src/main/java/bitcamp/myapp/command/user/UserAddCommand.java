package bitcamp.myapp.command.user;

import bitcamp.dao.UserDao;
import bitcamp.myapp.command.Command;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;
import java.util.List;

public class UserAddCommand implements Command {

  private UserDao userDao;

  public UserAddCommand(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);
    User user = new User();
    user.setName(Prompt.input("이름?"));
    user.setEmail(Prompt.input("이메일?"));
    user.setPassword(Prompt.input("암호?"));
    user.setTel(Prompt.input("연락처?"));
    user.setNo(User.getNextSeqNo());
    userDao.insert(user);
  }
}
