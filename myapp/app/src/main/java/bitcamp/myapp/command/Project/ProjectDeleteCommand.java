package bitcamp.myapp.command.Project;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProjectDeleteCommand implements Command {

    private ProjectDao projectDao;

    public ProjectDeleteCommand(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        int projectNo = Prompt.inputInt("프로젝트 번호?");
        try {
            Project deletedProject = projectDao.findBy(projectNo);
            if (deletedProject == null) {
                System.out.println("없는 프로젝트입니다.");
                return;
            }
            if (projectDao.delete(projectNo)) {
                System.out.printf("%d번 프로젝트를 삭제 했습니다.\n", deletedProject.getNo());
            } else {
                System.out.println("삭제 실패");
            }
        } catch (Exception e) {
            System.out.println("프로젝트 삭제 중 오류 발생");
        }
    }
}
