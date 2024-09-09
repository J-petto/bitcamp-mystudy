package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
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
@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {

  private BoardDao boardDao;
  private SqlSessionFactory sqlSessionFactory;
  private String uploadPath;

  @Override
  public void init() throws ServletException {
    this.boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    this.sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSession");
    this.uploadPath = this.getServletContext().getRealPath("/upload/board");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      User loginUser = (User) req.getSession().getAttribute("loginUser");

      int boardNo = Integer.parseInt(req.getParameter("no"));
      Board board = boardDao.findBy(boardNo);

      if (board == null) {
        throw new Exception("없는 게시글입니다.");
      } else if (loginUser == null || loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("변경 권한이 없습니다.");
      }

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

      boardDao.update(board);
      if (!board.getAttachedFiles().isEmpty()){
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
