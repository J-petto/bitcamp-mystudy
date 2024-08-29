package bitcamp.myapp.servlet.board;

import bitcamp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import bitcamp.net.Prompt;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BoardDeleteServlet extends GenericServlet {

  private BoardDao boardDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSession");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();
    try {
      User loginUser = (User) this.getServletContext().getAttribute("loginUser");

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("    <head>");
      out.println("        <link rel='stylesheet' href='/css/common.css'>");
      out.println("        <meta http-equiv='refresh' content='1;url=/board/list'>");
      out.println("        <title>게시글 삭제 결과</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
      out.println("<h1>게시글 삭제 결과</h1>");

      int boardNo = Integer.parseInt(req.getParameter("no"));

      if (loginUser.getNo() > 10 && boardNo != loginUser.getNo()) {
        out.println("<p>변경 권한이 없습니다.</p>");

        out.println("    </body>");
        out.println("</html>");
        return;
      }


      if(boardDao.delete(boardNo)) {
        sqlSessionFactory.openSession(false).commit();
        out.println("<p>삭제 했습니다.</p>");
      }else {
        out.println("<p>없는 게시글입니다.</p>");
      }


    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>삭제 중 오류 발생!<p>");
    }

    out.println("    </body>");
    out.println("</html>");
  }

}
