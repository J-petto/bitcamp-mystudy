package bitcamp.myapp.command.Project;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProjectListCommand implements Command {

    private Map<Integer, Project> projectMap;
    private List<Integer> projectNoList;

    public ProjectListCommand(Map<Integer, Project> projectMap, List<Integer> projectNoList) {
        this.projectMap = projectMap;
        this.projectNoList = projectNoList;
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        System.out.println("번호 프로젝트 기간");
        for (Integer no : projectNoList) {
            Project project = projectMap.get(no);
            System.out.printf("%d %s %s ~ %s\n",
                    project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
        }
    }

}
