package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/update")
public class BoardUpdateServlet extends GenericServlet {

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
      out.println("        <title>게시글 변경 결과</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
      out.println("<h1>게시글 변경 결과</h1>");

      int boardNo = Integer.parseInt(req.getParameter("no"));
      Board findBoard = boardDao.findBy(boardNo);
      if(loginUser == null || loginUser.getNo() > 10 && findBoard.getWriter().getNo() != loginUser.getNo()){
        out.println("<p>변경 권한이 없습니다.</p>");

        out.println("    </body>");
        out.println("</html>");
        return;
      }

      Board board = new Board();
      board.setNo(Integer.parseInt(req.getParameter("no")));
      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));

      if(boardDao.update(board)){
        sqlSessionFactory.openSession(false).commit();
        out.println("<p>변경 했습니다.</p>");
      }else {
        out.println("<p>없는 게시글입니다.</p>");
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>변경 중 오류 발생!</p>");
      e.printStackTrace();
    }

    out.println("    </body>");
    out.println("</html>");
  }
}
