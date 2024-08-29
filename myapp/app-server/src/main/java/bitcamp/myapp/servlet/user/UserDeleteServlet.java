package bitcamp.myapp.servlet.user;

import bitcamp.command.Command;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import bitcamp.net.Prompt;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/delete")
public class UserDeleteServlet extends GenericServlet {

  private UserDao userDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
    // 서블릿 컨테이너 ---> init(ServletConfig) ---> init() 호출함
    userDao = (UserDao) this.getServletContext().getAttribute("userDao");
    sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSession");
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
      out.println("        <meta http-equiv='refresh' content='1;url=/user/list' >");
      out.println("        <title>회원 삭제 결과</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
      out.println("<h1>회원 삭제 결과</h1>");

      int userNo = Integer.parseInt(req.getParameter("no"));

      if(userDao.delete(userNo)){
        sqlSessionFactory.openSession(false).commit();
        out.println("<p>삭제 했습니다.</p>");
      }else {
        out.println("<p>없는 게시글입니다.</p>");
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>삭제 중 오류 발생!</p>");
    }

    out.println("    </body>");
    out.println("</html>");
  }
}