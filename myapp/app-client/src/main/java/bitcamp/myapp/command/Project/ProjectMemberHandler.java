package bitcamp.myapp.command.project;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProjectMemberHandler {

    private UserDao userDao;

    public ProjectMemberHandler(Connection con, UserDao userDao) {
        this.userDao = userDao;
    }

    public void addMembers(Project project) throws Exception {
        while (true) {
            int userNo = Prompt.inputInt("추가할 팀원 번호?(종료: 0)");
            if (userNo == 0) {
                break;
            }

            User user = userDao.findBy(userNo);
            if (user == null) {
                System.out.println("없는 팀원입니다.");
                continue;
            }

            if (project.getMembers().contains(user)) {
                System.out.printf("'%s'은 현재 팀원입니다.\n", user.getName());
                continue;
            }

            project.getMembers().add(user);
            System.out.printf("'%s'을 추가했습니다.\n", user.getName());
        }
    }

    public void deleteMembers(Project project) throws Exception {
        Object[] members = project.getMembers().toArray();
        for (Object obj : members) {
            User member = (User) obj;
            String str = Prompt.input("팀원(%s) 삭제?", member.getName());
            if (str.equalsIgnoreCase("y")) {
                project.getMembers().remove(member);
                System.out.printf("'%s' 팀원을 삭제합니다.\n", member.getName());
            } else {
                System.out.printf("'%s' 팀원을 유지합니다.\n", member.getName());
            }
        }
    }
}
