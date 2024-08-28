package bitcamp.myapp.servlet.auth;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/login")
public class LoginServlet extends GenericServlet {

  private UserDao userDao;

  @Override
  public void init() throws ServletException {
    // 서블릿 컨테이너 ---> init(ServletConfig) ---> init() 호출함
    userDao = (UserDao) this.getServletContext().getAttribute("userDao");
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
      out.println("        <title>로그인 결과</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
      out.println("<h1>로그인 결과</h1>");

      String email = req.getParameter("email");
      String password = req.getParameter("password");

      User loginUser = userDao.findByEmailAndPassword(email, password);
      if (loginUser == null) {
        out.println("<p>이메일 또는 암호가 맞지 않습니다.</p>");

        out.println("    </body>");
        out.println("</html>");
        return;
      }

      // HTTP 프로토콜 관련 기능을 사용하려명 파라미터로 받은 ServletRequest 객체를 원래 타입으로 형변환 해야함
      // 즉, req 레퍼런스는 실제 HttpServletRequest 객체를 가리키고 있음.
      HttpServletRequest httpReq = (HttpServletRequest) req;
      // 클라이언트 전용 보관소를 알아냄
      HttpSession session = httpReq.getSession();

      session.setAttribute("loginUser", loginUser);
      out.printf("<p>로그인 성공</p>");
    } catch (Exception e) {
      out.println("<p>로그인 중 오류 발생!</p>");
    }

    out.println("    </body>");
    out.println("</html>");
  }
}
