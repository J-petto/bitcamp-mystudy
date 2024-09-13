package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet("/project/update")
public class ProjectUpdateServlet extends HttpServlet {

  private ProjectService projectService;

  @Override
  public void init() throws ServletException {
    projectService = (ProjectService) this.getServletContext().getAttribute("projectService");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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

      if(!projectService.update(project)){
        throw new Exception("없는 프로젝트입니다.");
      }

      req.setAttribute("viewName", "redirect:list");

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }

  }

}
