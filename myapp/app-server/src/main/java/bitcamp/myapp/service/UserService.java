package bitcamp.myapp.service;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserService {

    private UserDao userDao;
    private SqlSessionFactory sqlSessionFactory;

    public UserService(UserDao userDao, SqlSessionFactory sqlSessionFactory) {
        this.userDao = userDao;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void add(User user) throws Exception {
        try {
            userDao.insert(user);
            sqlSessionFactory.openSession(false).commit();

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    public List<User> list() throws Exception {
        return userDao.list();
    }

    public User get(int userNo) throws Exception {
        return userDao.findBy(userNo);
    }

    public boolean update(User user) throws Exception {
        try {
            if(userDao.update(user)){
                sqlSessionFactory.openSession(false).commit();
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    public boolean delete(int userNo) throws Exception {
        try {
            if(userDao.delete(userNo)){
                sqlSessionFactory.openSession(false).commit();
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }
}
