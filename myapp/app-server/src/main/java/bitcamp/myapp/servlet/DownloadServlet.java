package bitcamp.myapp.servlet;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

  private BoardDao boardDao;
  private Map<String, String> downloadPathMap = new HashMap<>();

  @Override
  public void init() throws ServletException {
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    downloadPathMap.put("board",this.getServletContext().getRealPath("/upload/board"));
    downloadPathMap.put("user",this.getServletContext().getRealPath("/upload/user"));
    downloadPathMap.put("project",this.getServletContext().getRealPath("/upload/project"));
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      User loginUser = (User) req.getSession().getAttribute("loginUser");
      if(loginUser == null) {
        throw new Exception("로그인을 하지않았습니다.");
      }

      String path = req.getParameter("path");
      String downloadDir = downloadPathMap.get(path);

      if (path.equals("board")) {
        int fileNo = Integer.parseInt(req.getParameter("fileNo"));
        AttachedFile attachedFile = boardDao.getFile(fileNo);

        res.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", attachedFile.getOriginFilename()));

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(downloadDir + "/" + attachedFile.getFilename()));
        OutputStream out = res.getOutputStream();

        int b;
        while ((b = in.read()) != -1) {
          out.write(b);
        }

        in.close();
        out.close();

      }else if (path.equals("user")) {

      }else {

      }

      req.setAttribute("viewName", "/board/form.jsp");
    }catch (Exception e) {
      req.setAttribute("exception", e);
    }

  }
}
