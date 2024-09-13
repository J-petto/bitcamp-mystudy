package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/project/form2")
public class ProjectForm2Servlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) this.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            //form1페이지에서 입력한 값을 Project 객체에 담은 후 세션에 보관
            Project project = new Project();
            project.setTitle(req.getParameter("title"));
            project.setDescription(req.getParameter("description"));
            project.setStartDate(Date.valueOf(req.getParameter("startDate")));
            project.setEndDate(Date.valueOf(req.getParameter("endDate")));

            HttpSession session = req.getSession();
            session.setAttribute("project", project);

            List<User> users = userService.list();
            req.setAttribute("users", users);

            req.setAttribute("viewName", "/project/form2.jsp");
        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").include(req, res);
        }

    }

}
