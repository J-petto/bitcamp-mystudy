package bitcamp.myapp.service;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

  private UserDao userDao;

  public DefaultUserService(UserDao userDao) {
    this.userDao = userDao;
  }

  @Transactional
  @Override
  public void add(User user) throws Exception {
      userDao.insert(user);
  }

  @Override
  public List<User> list() throws Exception {
    return userDao.list();
  }

  @Override
  public User get(int userNo) throws Exception {
    return userDao.findBy(userNo);
  }

  @Override
  public User exists(String email, String password) throws Exception {
    return userDao.findByEmailAndPassword(email, password);
  }

  @Transactional
  @Override
  public boolean update(User user) throws Exception {
      return userDao.update(user);
  }

  @Transactional
  @Override
  public boolean delete(int userNo) throws Exception {
      return userDao.delete(userNo);
  }
}
