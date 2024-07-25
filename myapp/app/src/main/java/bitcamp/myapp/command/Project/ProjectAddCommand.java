package bitcamp.myapp.command.Project;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProjectAddCommand implements Command {

    private ProjectDao projectDao;
    private ProjectMemberHandler memberHandler;

    public ProjectAddCommand(ProjectDao projectDao, ProjectMemberHandler memberHandler) {
        this.projectDao = projectDao;
        this.memberHandler = memberHandler;
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        Project project = new Project();
        project.setTitle(Prompt.input("프로젝트명?"));
        project.setDescription(Prompt.input("설명?"));
        project.setStartDate(Prompt.input("시작일?"));
        project.setEndDate(Prompt.input("종료일?"));

        System.out.println("팀원:");
        memberHandler.addMembers(project);

        try {
            projectDao.insert(project);
        }catch (Exception e){
            System.out.println("프로젝트 추가 중 오류 발생");
        }

        System.out.println("등록했습니다.");
    }

}
