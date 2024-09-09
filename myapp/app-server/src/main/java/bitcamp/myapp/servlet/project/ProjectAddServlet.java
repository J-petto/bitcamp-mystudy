package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
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

  private ProjectDao projectDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
    ServletContext ctx = this.getServletContext();
    projectDao = (ProjectDao) ctx.getAttribute("projectDao");
    sqlSessionFactory = (SqlSessionFactory) ctx.getAttribute("sqlSession");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      req.setCharacterEncoding("UTF-8");

      Project project = (Project) req.getSession().getAttribute("project");
      projectDao.insert(project);

      if (project.getMembers() != null && !project.getMembers().isEmpty()) {
        projectDao.insertMembers(project.getNo(), project.getMembers());
      }

      sqlSessionFactory.openSession(false).commit();
      res.sendRedirect("/project/list");

      req.getSession().removeAttribute("project");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").include(req, res);
    }
  }

}
