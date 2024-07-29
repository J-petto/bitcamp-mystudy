package bitcamp.dao;

import bitcamp.myapp.vo.User;

import java.util.List;

public interface UserDao {
    void insert(User user);
    List<User> list();
    User findBy(int no);
    void update(User user);
    void delete(int no);
}
