package bitcamp.myapp.servlet.board;

import bitcamp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import bitcamp.net.Prompt;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/add")
public class BoardAddServlet extends GenericServlet {

  private BoardDao boardDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
    ServletContext ctx = this.getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
    sqlSessionFactory = (SqlSessionFactory) ctx.getAttribute("sqlSession");
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
      out.println("        <meta http-equiv='refresh' content='1;url=/board/list' >");
      out.println("        <title>게시글 등록</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
      out.println("<h1>게시글 등록</h1>");

      Board board = new Board();
      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));

      User loginUser = (User) ((HttpServletRequest) req).getSession().getAttribute("loginUser");

      if(loginUser == null){

      }else {
        board.setWriter(loginUser);
      }

      boardDao.insert(board);
      sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공</p>");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 중 오류 발생!</p>");
      e.printStackTrace();
    }

    out.println("    </body>");
    out.println("</html>");
  }

}