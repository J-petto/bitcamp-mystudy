package bitcamp.myapp.servlet.auth;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/logout")
public class LogoutServlet extends GenericServlet {

  private UserDao userDao;


  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    ((HttpServletRequest) req).getSession().invalidate();
    ((HttpServletResponse) res).sendRedirect("/");
  }
}
