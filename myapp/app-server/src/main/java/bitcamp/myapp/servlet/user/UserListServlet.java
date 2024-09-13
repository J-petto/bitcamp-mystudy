package bitcamp.myapp.servlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/list")
public class UserListServlet extends HttpServlet {

  private UserService userService;

  @Override
  public void init() {
    userService = (UserService) getServletContext().getAttribute("userService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      List<User> list = userService.list();
      req.setAttribute("list", list); // JSP를 실행하기 전에 JSP가 사용할 객체를 ServletRequest 보관소에 보관한다.
      req.setAttribute("viewName", "/user/list.jsp");
    }catch (Exception e){
      req.setAttribute("exception", e);
    }
  }
}
