package bitcamp.myapp.command.Project;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProjectListCommand implements Command {

    private ProjectDao projectDao;

    public ProjectListCommand(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public void execute(String menuName) {
        try {
            System.out.printf("[%s]\n", menuName);

            System.out.println("번호 프로젝트 기간");

            for (Project project : projectDao.list()) {
                System.out.printf("%d %s %s ~ %s\n",
                        project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
            }
        } catch (Exception e) {
            System.out.println("프로젝트 목록 조회 중 오류 발생");
        }
    }

}
