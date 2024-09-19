package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.annotation.RequestParam;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class DownloadController {

    private BoardService boardService;
    private Map<String, String> downloadPathMap = new HashMap<>();

    public DownloadController(BoardService boardService, ServletContext ctx) {
        this.boardService = boardService;
        downloadPathMap.put("board", ctx.getRealPath("/upload/board"));
        downloadPathMap.put("user", ctx.getRealPath("/upload/user"));
        downloadPathMap.put("project", ctx.getRealPath("/upload/project"));
    }

    @RequestMapping("/download")
    public void download(@RequestParam("path") String path, @RequestParam("fileNo") int fileNo, HttpSession session, HttpServletResponse res) throws Exception {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new Exception("로그인을 하지않았습니다.");
        }

        String downloadDir = downloadPathMap.get(path);

        if (path.equals("board")) {
            AttachedFile attachedFile = boardService.getAttachedFile(fileNo);

            res.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", attachedFile.getOriginFilename()));

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(downloadDir + "/" + attachedFile.getFilename()));
            OutputStream out = res.getOutputStream();

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }

            in.close();
            out.close();

        } else if (path.equals("user")) {

        } else {

        }
    }
}
