package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/project/view")
public class ProjectViewServlet extends GenericServlet {

  private ProjectDao projectDao;

  @Override
  public void init() throws ServletException {
    this.projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();

    try {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("    <head>");
      out.println("        <title>프로젝트 조회</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<h1>프로젝트 조회</h1>");
      int projectNo = Integer.parseInt(req.getParameter("no"));

      Project project = projectDao.findBy(projectNo);
      if (project == null) {
        out.println("<p>없는 프로젝트입니다.</p>");
        return;
      }

      out.printf("<p>프로젝트명: %s</p>", project.getTitle());
      out.printf("<p>설명: %s</p>", project.getDescription());
      out.printf("<p>기간: %s ~ %s</p>", project.getStartDate(), project.getEndDate());

      out.println("<p>팀원:</p>");
      out.println("<ul>");
      for (User user : project.getMembers()) {
        out.printf("<li>%s</li>", user.getName());
      }
      out.println("</ul>");
    } catch (Exception e) {
      out.println("<p>조회 중 오류 발생!</p>");
    }
    out.println("    </body>");
    out.println("</html>");
  }
}
