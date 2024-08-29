package bitcamp.myapp.servlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/view")
public class UserViewServlet extends GenericServlet {

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
            out.println("        <title>회원 조회</title>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
            out.println("<h1>회원 조회</h1>");
            int userNo = Integer.parseInt(req.getParameter("no"));

            User user = userDao.findBy(userNo);
            if (user == null) {
                out.println("<p>없는 회원입니다.</p>");

                out.println("    </body>");
                out.println("</html>");
                return;
            }

            out.printf("<form action='/user/update'>");
            out.printf("    <p>번호: <input name='no' type='text' value='%s' readonly></p>", user.getNo());
            out.printf("    <p>이름: <input name='name' type='text' value='%s'></p>", user.getName());
            out.printf("    <p>이메일: <input name='email' type='email' value='%s'></p>", user.getEmail());
            out.printf("    <p>암호: <input name='password' type='password'></p>");
            out.printf("    <p>연락처: <input name='tel' type='tel' value='%s'></p>", user.getTel());
            out.printf("    <button>변경하기</button>");
            out.printf("    <button type='button' onclick='location.href=\"/user/delete?no=%d\"'>삭제하기</button>", user.getNo());
            out.printf("</form>");

        } catch (Exception e) {
            out.println("<p>조회 중 오류 발생!</p>");
        }

        out.println("    </body>");
        out.println("</html>");
    }
}
