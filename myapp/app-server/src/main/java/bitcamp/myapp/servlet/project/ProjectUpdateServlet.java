package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet("/project/update")
public class ProjectUpdateServlet extends GenericServlet {

  private ProjectDao projectDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
    projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
    sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSession");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    try {
      Project project = new Project();
      project.setNo(Integer.parseInt(req.getParameter("no")));
      project.setTitle(req.getParameter("title"));
      project.setDescription(req.getParameter("description"));
      project.setStartDate(Date.valueOf((req.getParameter("startDate"))));
      project.setEndDate(Date.valueOf(req.getParameter("endDate")));

      String[] memberNumbers = req.getParameterValues("member");
      if(memberNumbers != null){
        ArrayList<User> members = new ArrayList<>();
        for(String memberNo : memberNumbers){
          members.add(new User(Integer.parseInt(memberNo)));
        }
        project.setMembers(members);
      }

      if(!projectDao.update(project)){
        throw new Exception("없는 프로젝트입니다.");
      }

      projectDao.deleteMembers(project.getNo());

      if(project.getMembers() != null && !project.getMembers().isEmpty()){
        projectDao.insertMembers(project.getNo(), project.getMembers());
      }
      sqlSessionFactory.openSession(false).commit();
      ((HttpServletResponse) res).sendRedirect("/project/list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").include(req, res);
    }

  }

}
