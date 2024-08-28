package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/project/form")
public class ProjectFormServlet extends GenericServlet {

  private UserDao userDao;

  @Override
  public void init() throws ServletException {
    this.userDao = (UserDao) this.getServletContext().getAttribute("userDao");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();

    try {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("    <head>");
      out.println("        <link rel='stylesheet' href='/css/common.css'>");
      out.println("        <title>프로젝트 조회</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");

      out.println("<h1>프로젝트 등록</h1>");

      out.println("<form action='/project/add'>");
      out.println("    <p>타이틀: <input name='title' type='text'></p>");
      out.println("    <p>내용: <textarea name='description'></textarea></p>");
      out.println("    <p>시작일: <input name='startDate' type='date'></p>");
      out.println("    <p>종료일: <input name='endDate' type='date'></p>");
      out.println("    팀원:<br>");
      List<User> users = userDao.list();
      out.printf("<ul>");
      for(User user : users){
        out.printf("<li><input name='member' value='%d' type='checkbox'>%s</li>", user.getNo(), user.getName());
      }
      out.printf("</ul>");
      out.println("    <input type='submit' value='등록하기'>" );
      out.println("</form>");

    } catch (Exception e) {
      out.println("<p>조회 중 오류 발생!</p>");
    }
    out.println("    </body>");
    out.println("</html>");
  }
}
