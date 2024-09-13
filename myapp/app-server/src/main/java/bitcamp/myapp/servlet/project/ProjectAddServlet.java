package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.vo.Project;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/project/add")
public class ProjectAddServlet extends HttpServlet {

  private ProjectService projectService;

  @Override
  public void init() throws ServletException {
    projectService = (ProjectService) this.getServletContext().getAttribute("projectService");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      Project project = (Project) req.getSession().getAttribute("project");
      projectService.add(project);

      req.setAttribute("viewName", "redirect:list");

      req.getSession().removeAttribute("project");
    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }

}
