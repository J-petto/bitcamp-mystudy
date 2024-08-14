package bitcamp.myapp.listener;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.myapp.command.HelpCommand;
import bitcamp.myapp.command.HistoryCommand;
import bitcamp.myapp.command.board.*;
import bitcamp.myapp.command.project.*;
import bitcamp.myapp.command.user.*;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.dao.mysql.BoardDaoImpl;
import bitcamp.myapp.dao.mysql.ProjectDaoImpl;
import bitcamp.myapp.dao.mysql.UserDaoImpl;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class AuthApplicationListener implements ApplicationListener {

    @Override
    public boolean onStart(ApplicationContext ctx) throws Exception {
        String email = Prompt.input("이메일?");
        String password = Prompt.input("암호??");

        UserDao userDao = (UserDao) ctx.getAttribute("userDao");

        User user = userDao.findByEmailAndPassword(email, password);
        if (user == null) {
            System.out.println("로그인 실패");
            return false;
        }

        // 로그인 사용자 정보를 쓸 수 있도록 보관
        ctx.setAttribute("loginUser", user);
        return true;
    }
}
