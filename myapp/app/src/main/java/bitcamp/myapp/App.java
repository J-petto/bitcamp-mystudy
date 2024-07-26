package bitcamp.myapp;

import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.myapp.command.Board.*;
import bitcamp.myapp.command.HelpCommand;
import bitcamp.myapp.command.HistoryCommand;
import bitcamp.myapp.command.Project.*;
import bitcamp.myapp.command.User.*;
import bitcamp.myapp.dao.*;
import bitcamp.myapp.vo.SequenceNo;
import bitcamp.util.Prompt;

import java.lang.reflect.Method;
import java.util.*;

public class App {

    MenuGroup mainMenu = new MenuGroup("메인");

    UserDao userDao;
    BoardDao boardDao;
    ProjectDao projectDao;

    HelpCommand helpCommand;
    HistoryCommand historyCommand;

    public App() {
//        loadData();

        helpCommand = new HelpCommand();
        historyCommand = new HistoryCommand();

        userDao = new ListUserDao("data.xlsx");
        boardDao = new ListBoardDao("data.xlsx");
        projectDao = new ListProjectDao("data.xlsx", userDao);

        MenuGroup userMenu = new MenuGroup("회원");
        userMenu.add(new MenuItem("등록", new UserAddCommand(userDao)));
        userMenu.add(new MenuItem("목록", new UserListCommand(userDao)));
        userMenu.add(new MenuItem("조회", new UserViewCommand(userDao)));
        userMenu.add(new MenuItem("변경", new UserUpdateCommand(userDao)));
        userMenu.add(new MenuItem("삭제", new UserDeleteCommand(userDao)));
        mainMenu.add(userMenu);

        MenuGroup projectMenu = new MenuGroup("프로젝트");
        ProjectMemberHandler memberHandler = new ProjectMemberHandler(userDao);
        projectMenu.add(new MenuItem("등록", new ProjectAddCommand(projectDao, memberHandler)));
        projectMenu.add(new MenuItem("목록", new ProjectListCommand(projectDao)));
        projectMenu.add(new MenuItem("조회", new ProjectViewCommand(projectDao)));
        projectMenu.add(new MenuItem("변경", new ProjectUpdateCommand(projectDao, memberHandler)));
        projectMenu.add(new MenuItem("삭제", new ProjectDeleteCommand(projectDao)));
        mainMenu.add(projectMenu);

        MenuGroup boardMenu = new MenuGroup("게시판");
        boardMenu.add(new MenuItem("등록", new BoardAddCommand(boardDao)));
        boardMenu.add(new MenuItem("목록", new BoardListCommand(boardDao)));
        boardMenu.add(new MenuItem("조회", new BoardViewCommand(boardDao)));
        boardMenu.add(new MenuItem("변경", new BoardUpdateCommand(boardDao)));
        boardMenu.add(new MenuItem("삭제", new BoardDeleteCommand(boardDao)));
        mainMenu.add(boardMenu);

        mainMenu.add(new MenuItem("도움말", helpCommand));
        mainMenu.add(new MenuItem("명령내역", historyCommand));

        mainMenu.setExitTitle("종료");
    }

    public static void main(String[] args) {
        new App().execute();
    }

    void execute() {
        try {
            mainMenu.execute();
        } catch (Exception ex) {
            System.out.println("실행 오류");
            ex.printStackTrace();
        } finally {
            saveData();
        }

        System.out.println("종료합니다.");

        Prompt.close();
    }

    private void saveData() {
        try {
            ((ListUserDao) userDao).save();
            ((ListProjectDao) projectDao).save();
            ((ListBoardDao) boardDao).save();

            System.out.println("데이터를 저장했습니다.");
        } catch (Exception e) {
            System.out.println("데이터 저장 중 오류 발생");
            e.printStackTrace();
            System.out.println();
        }
    }

    private <E> void initSeqNo(Collection<E> list, Class<E> elementType) throws Exception /*NoSuchMethodException, InvocationTargetException, IllegalAccessException*/ {
        int maxSeqNo = 0;
        for (Object element : list) {
            SequenceNo seqObj = (SequenceNo) element;
            if (seqObj.getNo() > maxSeqNo) {
                maxSeqNo = seqObj.getNo();
            }
        }
        Method method = elementType.getMethod("initSeqNo", int.class);
        method.invoke(null, maxSeqNo);
    }
}
