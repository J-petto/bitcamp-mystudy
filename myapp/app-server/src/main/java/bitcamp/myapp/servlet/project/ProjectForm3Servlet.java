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
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/project/form3")
public class ProjectForm3Servlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) this.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // 세션에 보관된 프로젝트 기본 정보 꺼냄
            Project project = (Project) req.getSession().getAttribute("project");

            // form2 페이지에서 사용자가 선택한 팀원 정보를 프로젝트에 저장함
            String[] memberNumbers = req.getParameterValues("member");
            if(memberNumbers != null){
                ArrayList<User> members = new ArrayList<>();
                for(String memberNo : memberNumbers){
                    members.add(userService.get(Integer.parseInt(memberNo)));
                }
                project.setMembers(members);
            }

            res.setContentType("text/html;charset=UTF-8");
            req.getRequestDispatcher("/project/form3.jsp").include(req, res);

        }catch (Exception e){
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").include(req, res);
        }

    }

}
