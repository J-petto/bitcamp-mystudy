package bitcamp.myapp.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 60,
        maxRequestSize = 1024 * 1024 * 100
)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
//    req.getServletPath(); // @WebServlet() 주소인 /app 까지를 가리킴        ex. http://localhost:8888/app/board/list : /app
//      req.getPathInfo()   // @WebServlet() 주소의 /app을 제외한 *를 가리킴  ex. http://localhost:8888/app/board/list : /board/list
      req.getRequestDispatcher(req.getPathInfo()).include(req, res);

      // 실행시킨 페이지 컨트롤러에서 예외가 발생했다면, 오류 페이지로 보낸다.
      Exception exception = (Exception) req.getAttribute("exception");
      if (exception != null) {
        throw exception;
      }

      // 쿠키 처리
      Enumeration<String> attrNames = req.getAttributeNames();
      while (attrNames.hasMoreElements()) {
        Object attrValue = req.getAttribute(attrNames.nextElement());
        if(attrValue instanceof Cookie){
          res.addCookie((Cookie) attrValue);
        }
      }

      // 페이지 컨트롤러가 정상적으로 실행했으면, viewName을 가져와 포워딩 함
      String viewName = (String) req.getAttribute("viewName");
      if(viewName.startsWith("redirect:")) {
        res.sendRedirect(viewName.substring(9));
      }else {
        String refresh = (String) req.getAttribute("refresh");
        if(refresh != null) {
          res.setHeader("Refresh", refresh);
        }
        req.getRequestDispatcher(viewName).forward(req, res);
      }
    }catch (Exception e) {
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }

  }
}
