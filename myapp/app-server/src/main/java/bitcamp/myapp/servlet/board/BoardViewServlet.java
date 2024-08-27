package bitcamp.myapp.servlet.board;

import bitcamp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.net.Prompt;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/view")
public class BoardViewServlet extends GenericServlet {

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
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("    <head>");
      out.println("        <title>게시글 조회</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<h1>게시글 조회</h1>");
      int boardNo = Integer.parseInt(req.getParameter("no"));

      Board board = boardDao.findBy(boardNo);
      if (board == null) {
        out.println("<p>없는 게시글입니다.</p>");
        return;
      }

      board.setViewCount(board.getViewCount() + 1);
      boardDao.updateViewCount(board.getNo(), board.getViewCount());
      sqlSessionFactory.openSession(false).commit();

      out.printf("<p>제목: %s</p>", board.getTitle());
      out.printf("<p>내용: %s</p>", board.getContent());
      out.printf("<p>작성일: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS</p>", board.getCreatedDate());
      out.printf("<p>조회수: %d</p>", board.getViewCount());
      out.printf("<p>작성자: %s</p>", board.getWriter().getName());

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>조회 중 오류 발생!</p>");
      e.printStackTrace();
    }

    out.println("    </body>");
    out.println("</html>");
  }
}
