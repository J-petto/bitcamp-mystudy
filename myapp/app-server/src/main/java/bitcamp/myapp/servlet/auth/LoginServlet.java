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

//    ((HttpServletResponse) res).setHeader("Refresh","1;url=/"); 버퍼를 출력(페이지를 보여주고) 링크로 이동

    try {
      String email = req.getParameter("email");
      String password = req.getParameter("password");

      User loginUser = userDao.findByEmailAndPassword(email, password);

      if (loginUser == null) {
        ((HttpServletResponse) res).setHeader("Refresh", "2;url=/auth/form");
        req.getRequestDispatcher("/auth/fail.jsp").include(req, res);
        return;// 버퍼 값을 초기화 시키고 바로 이동
      }


      // HTTP 프로토콜 관련 기능을 사용하려명 파라미터로 받은 ServletRequest 객체를 원래 타입으로 형변환 해야함
      // 즉, req 레퍼런스는 실제 HttpServletRequest 객체를 가리키고 있음.
      HttpServletRequest httpReq = (HttpServletRequest) req;
      // 클라이언트 전용 보관소를 알아냄
      HttpSession session = httpReq.getSession();

      session.setAttribute("loginUser", loginUser);

      ((HttpServletResponse) res).sendRedirect("/"); // 버퍼 값을 초기화 시키고 바로 이동
    } catch (Exception e) {
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").include(req, res);
    }
  }
}
