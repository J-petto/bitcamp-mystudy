package bitcamp.myapp.servlet.user;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/form")
public class UserFormServlet extends GenericServlet {
  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();

    req.getRequestDispatcher("/header").include(req, res);

    out.println("<h1>회원 등록</h1>");
    out.println("<form action='/user/add'>");
    out.println("    <p>이름: <input name='name' type='text'></p>");
    out.println("    <p>이메일: <input name='email' type='email'></p>");
    out.println("    <p>암호: <input name='password' type='password'></p>");
    out.println("    <p>연락처: <input name='tel' type='tel'></p>");
    out.println("    <input type='submit' value='등록하기'>");
    out.println("</form>");


    out.println("</body>");
    out.println("</html>");
  }
}
