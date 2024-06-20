package bitcamp.myapp.command;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

public class ProjectCommand {
    private static final int PROJECT_MAX = 50;
    private static Project[] projects = new Project[PROJECT_MAX];
    private static int projectLength = 0;

    public static void autoProject() {
        for (int i = 0; i < 3; i++) {
            Project project = new Project();
            int id = i + 1;
            project.setTitle("프로젝트" + id);
            project.setExplanation("설명" + id);
            project.setStartDate("2024-01-0" + id);
            project.setEndDate("2024-02-0" + id);
            for (int j = 0; j < 3; j++) {
                User user = UserCommand.getUserByNo(j + 1);
                project.addMember(user);
            }
            projects[projectLength++] = project;
        }
    }

    public static void userProcess(String subMenuTitle) {
        switch (subMenuTitle) {
            case "등록":
                createProject();
                break;
            case "목록":
                listProject();
                break;
            case "조회":
                searchProject();
                break;
            case "변경":
                updateProject();
                break;
            case "삭제":
                deleteProject();
                break;
            default:
        }
    }

    private static void createProject() {
        Project project = new Project();
        project.setTitle(Prompt.prompt("프로젝트명?"));
        project.setExplanation(Prompt.prompt("설명?"));
        project.setStartDate(Prompt.prompt("시작일?"));
        project.setEndDate(Prompt.prompt("종료일?"));
        System.out.println("팀원:");
        addMember(project);
        projects[projectLength++] = project;
        System.out.printf("%s 프로젝트를 등록했습니다.\n", project.getTitle());
    }

    private static void listProject() {
        System.out.println("번호 프로젝트명 기간");
        for (int i = 0; i < projectLength; i++) {
            Project project = projects[i];
            System.out.printf("%d. %s %s ~ %s\n", (i + 1), project.getTitle(), project.getStartDate(), project.getEndDate());
        }
    }

    private static void searchProject() {
        while (true) {
            try {
                int projectNo = Prompt.promptInt("프로젝트 번호?");
                Project project = getProjectByNo(projectNo);
                if (project == null) {
                    System.out.println("없는 프로젝트입니다.");
                    break;
                }
                System.out.printf("프로젝트명: %s\n", project.getTitle());
                System.out.printf("설명: %s\n", project.getExplanation());
                System.out.printf("기간: %s ~ %s\n", project.getStartDate(), project.getEndDate());
                System.out.println("팀원:");
                int memberLength = project.getMemberLength();
                for (int i = 0; i < memberLength; i++) {
                    User user = project.getMembers()[i];
                    System.out.printf("- %s\n", user.getName());
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요.");
            }
        }
    }

    private static void updateProject() {
        while (true) {
            try {
                int projectNo = Prompt.promptInt("프로젝트 번호?");
                Project project = getProjectByNo(projectNo);
                if (project == null) {
                    System.out.println("없는 프로젝트입니다.");
                    break;
                }
                project.setTitle(Prompt.prompt("이름(%s)?", project.getTitle()));
                project.setExplanation(Prompt.prompt("설명(%s)?", project.getExplanation()));
                project.setStartDate(Prompt.prompt("시작일(%s)?", project.getStartDate()));
                project.setEndDate(Prompt.prompt("종료일(%s)?", project.getEndDate()));
                System.out.println("팀원");
                deleteMember(project);
                addMember(project);
                System.out.println("변경하였습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요.");
            }
        }
    }

    private static void deleteProject() {
        while (true) {
            try {
                int projectNo = Prompt.promptInt("프로젝트 번호?");
                Project project = getProjectByNo(projectNo);
                if (project == null) {
                    System.out.println("없는 프로젝트입니다.");
                    break;
                }
                for (int i = projectNo; i < projectLength + 1; i++) {
                    projects[i - 1] = projects[i];
                }
                projects[--projectLength] = null;
                System.out.println("삭제하였습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요.");
            }
        }
    }

    private static void addMember(Project project) {
        while (true) {
            int addUserNo = Prompt.promptInt("추가할 팀원 번호?(종료: 0)");
            if (addUserNo == 0) {
                break;
            }
            User addUser = UserCommand.getUserByNo(addUserNo);
            if (addUser == null) {
                System.out.println("없는 팀원입니다.");
                continue;
            }
            if(project.isMember(addUser)){
                System.out.printf("'%s'은 현재 팀원입니다.\n", addUser.getName());
                continue;
            }
            project.addMember(addUser);
            System.out.printf("'%s'을 추가했습니다.\n", addUser.getName());
        }
    }

    private static void deleteMember(Project project) {
        for (int i = project.getMemberLength(); i > 0; i--) {
            User deleteUser = project.getMembers()[i - 1];
            while (true) {
                String command = Prompt.prompt("팀원(%s) 삭제?", deleteUser.getName());
                if (command.equalsIgnoreCase("y")) {
                    project.deleteMember(i);
                    System.out.printf("'%s' 팀원을 삭제합니다.\n", deleteUser.getName());
                    break;
                } else if (command.equalsIgnoreCase("n")) {
                    System.out.printf("'%s' 팀원을 유지합니다.\n", deleteUser.getName());
                    break;
                }
                System.out.println("y 나 n만 입력해주세요.\n");
            }

        }
    }

    private static Project getProjectByNo(int projectNo) {
        if (projectNo < 1 && projectNo > projectLength) {
            return null;
        }
        return projects[projectNo - 1];
    }
}
