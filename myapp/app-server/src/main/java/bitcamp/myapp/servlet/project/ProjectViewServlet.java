package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/project/view")
public class ProjectViewServlet extends GenericServlet {

  private ProjectDao projectDao;
  private UserDao userDao;

  @Override
  public void init() throws ServletException {
    this.projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
    this.userDao = (UserDao) this.getServletContext().getAttribute("userDao");
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
      out.println("        <title>프로젝트 조회</title>");
      out.println("    </head>");
      out.println("    <body>");
      out.println("<header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
      out.println("<h1>프로젝트 조회</h1>");
      int projectNo = Integer.parseInt(req.getParameter("no"));

      Project project = projectDao.findBy(projectNo);
      if (project == null) {
        out.println("<p>없는 프로젝트입니다.</p>");

        out.println("    </body>");
        out.println("</html>");
        return;
      }

      out.println("<form action='/project/update'>");
      out.printf("    <p>번호: <input name='no' type='text' value='%d' readonly></p>", project.getNo());
      out.printf("    <p>타이틀: <input name='title' type='text' value='%s'></p>", project.getTitle());
      out.printf("    <p>내용: <textarea name='description'>%s</textarea></p>", project.getDescription());
      out.printf("    <p>시작일: <input name='startDate' type='date' value='%s'></p>", project.getStartDate());
      out.printf("    <p>종료일: <input name='endDate' type='date' value='%s'></p>", project.getEndDate());
      out.println("    팀원:<br>");
      List<User> users = userDao.list();
      out.printf("<ul>");
      for(User user : users){
        out.printf("<li><input name='member' value='%d' type='checkbox' %s>%s</li>", user.getNo(), isMember(project.getMembers(), user) ? "checked" : "", user.getName());
      }
      out.printf("</ul>");
      out.println("    <button>수정하기</button>" );
      out.printf(    "<button type='button' onclick='location.href=\"/project/delete?no=%d\"'>삭제하기</button>", project.getNo());
      out.println("</form>");

    } catch (Exception e) {
      out.println("<p>조회 중 오류 발생!</p>");
    }
    out.println("    </body>");
    out.println("</html>");
  }

  private boolean isMember(List<User> members, User user){
    for(User member : members){
      if(member.equals(user)) return true;
    }
    return false;
  }
}
