package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 60,
        maxRequestSize = 1024 * 1024 * 100
)
@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet {

  private BoardDao boardDao;
  private SqlSessionFactory sqlSessionFactory;
  private String uploadPath;

  @Override
  public void init() throws ServletException {
    ServletContext ctx = this.getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
    sqlSessionFactory = (SqlSessionFactory) ctx.getAttribute("sqlSession");
    this.uploadPath = ctx.getRealPath("/upload/board");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    req.getRequestDispatcher("/board/form.jsp").include(req, res);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      User loginUser = (User) req.getSession().getAttribute("loginUser");
      if(loginUser == null) {
        throw new Exception("로그인을 하지않았습니다.");
      }

      Board board = new Board();
      board.setWriter(loginUser);
      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

      Collection<Part> parts = req.getParts(); // title 값, content 값, file 첨부 모두 포함
      for (Part part : parts) {
        if(!part.getName().equals("files") || part.getSize() == 0) {
          continue;
        }

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFilename(UUID.randomUUID().toString());
        attachedFile.setOriginFilename(part.getSubmittedFileName());

        part.write(uploadPath + "/" + attachedFile.getFilename());

        attachedFiles.add(attachedFile);
      }

      board.setAttachedFiles(attachedFiles);

      boardDao.insert(board);
      if(!board.getAttachedFiles().isEmpty()) {
        boardDao.insertFiles(board);
      }

      sqlSessionFactory.openSession(false).commit();
      res.sendRedirect("/board/list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").include(req, res);
    }
  }

}
