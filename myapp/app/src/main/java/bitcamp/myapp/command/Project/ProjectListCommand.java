package bitcamp.myapp.command.Project;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.util.Iterator;
import java.util.List;

public class ProjectListCommand implements Command {

    private List<Project> projectList;

    public ProjectListCommand(List<Project> projectList) {
        this.projectList = projectList;
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        System.out.println("번호 프로젝트 기간");
        for (Project project : projectList) {
            System.out.printf("%d %s %s ~ %s\n",
                    project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
        }
    }

}
