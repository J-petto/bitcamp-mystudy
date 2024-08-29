package bitcamp.myapp.servlet.project;

import bitcamp.command.Command;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.net.Prompt;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/project/delete")
public class ProjectDeleteServlet extends GenericServlet {

  private ProjectDao projectDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
    projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
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
      out.println("        <link rel='stylesheet' href='/css/common.css'>");
      out.println("        <meta http-equiv='refresh' content='1;url=/project/list' >");
      out.println("        <title>프로젝트 수정 결과</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
      out.println("<h1>프로젝트 수정 결과</h1>");

      int projectNo = Integer.parseInt(req.getParameter("no"));

      projectDao.deleteMembers(projectNo);
      if(projectDao.delete(projectNo)){
        sqlSessionFactory.openSession(false).commit();
        out.println("<p>삭제 했습니다.</p>");
      }else {
        out.println("<p>없는 프로젝트입니다.<p/>");
      }


    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>삭제 중 오류 발생!</p>");
    }

    out.println("    </body>");
    out.println("</html>");
  }
}
