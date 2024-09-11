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

public interface UserService {
    void add(User user) throws Exception;

    List<User> list() throws Exception;

    User get(int userNo) throws Exception;

    boolean update(User user) throws Exception;

    boolean delete(int userNo) throws Exception;

    User exists(String email, String password) throws Exception;
}
