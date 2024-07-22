package bitcamp.myapp;

import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.myapp.command.Board.*;
import bitcamp.myapp.command.HelpCommand;
import bitcamp.myapp.command.HistoryCommand;
import bitcamp.myapp.command.Project.*;
import bitcamp.myapp.command.User.*;
import bitcamp.myapp.vo.SequenceNo;
import bitcamp.util.Prompt;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class App {

    MenuGroup mainMenu = new MenuGroup("메인");

    HelpCommand helpCommand;
    HistoryCommand historyCommand;

    List<User> userList = new ArrayList<>();
    List<Project> projectList = new LinkedList<>();
    List<Board> boardList = new LinkedList<>();

    public App() {
        loadData();

        helpCommand = new HelpCommand();
        historyCommand = new HistoryCommand();

        MenuGroup userMenu = new MenuGroup("회원");
        userMenu.add(new MenuItem("등록", new UserAddCommand(userList)));
        userMenu.add(new MenuItem("목록", new UserListCommand(userList)));
        userMenu.add(new MenuItem("조회", new UserViewCommand(userList)));
        userMenu.add(new MenuItem("변경", new UserUpdateCommand(userList)));
        userMenu.add(new MenuItem("삭제", new UserDeleteCommand(userList)));
        mainMenu.add(userMenu);

        MenuGroup projectMenu = new MenuGroup("프로젝트");
        ProjectMemberHandler memberHandler = new ProjectMemberHandler(userList);
        projectMenu.add(new MenuItem("등록", new ProjectAddCommand(projectList, memberHandler)));
        projectMenu.add(new MenuItem("목록", new ProjectListCommand(projectList)));
        projectMenu.add(new MenuItem("조회", new ProjectViewCommand(projectList)));
        projectMenu.add(new MenuItem("변경", new ProjectUpdateCommand(projectList, memberHandler)));
        projectMenu.add(new MenuItem("삭제", new ProjectDeleteCommand(projectList)));
        mainMenu.add(projectMenu);

        MenuGroup boardMenu = new MenuGroup("게시판");
        boardMenu.add(new MenuItem("등록", new BoardAddCommand(boardList)));
        boardMenu.add(new MenuItem("목록", new BoardListCommand(boardList)));
        boardMenu.add(new MenuItem("조회", new BoardViewCommand(boardList)));
        boardMenu.add(new MenuItem("변경", new BoardUpdateCommand(boardList)));
        boardMenu.add(new MenuItem("삭제", new BoardDeleteCommand(boardList)));
        mainMenu.add(boardMenu);

        mainMenu.add(new MenuItem("도움말", helpCommand));
        mainMenu.add(new MenuItem("명령내역", historyCommand));

        mainMenu.setExitTitle("종료");
    }

    public static void main(String[] args) {
        new App().execute();
    }

    void execute() {
        String appTitle = "[프로젝트 관리 시스템]";
        String line = "----------------------------------";

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

    private void loadData() {
        loadJson(userList, "user.json", User.class);
        loadJson(projectList, "project.json", Project.class);
        loadJson(boardList, "board.json", Board.class);

        System.out.println("데이터를 로딩했습니다.");
    }

    private void saveData() {
        saveJson(userList, "user.json");
        saveJson(projectList, "project.json");
        saveJson(boardList, "board.json");
        System.out.println("데이터를 저장했습니다.");
    }

    private void saveJson(Object obj, String filename) {
        try (FileWriter out = new FileWriter(filename)) {

            out.write(new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create()
                    .toJson(obj));

        } catch (IOException e) {
            System.out.printf("%s 파일 저장 중 오류 발생\n", filename);
            e.printStackTrace();
        }
    }

    private <E> void initSeqNo(List<E> list, Class<E> elementType) throws Exception /*NoSuchMethodException, InvocationTargetException, IllegalAccessException*/ {
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

    private <E> void loadJson(List<E> list, String filename, Class<E> elementType) {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }

            list.addAll((List<E>) new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create().fromJson(
                            stringBuilder.toString(),
                            TypeToken.getParameterized(List.class, elementType)));

            // 읽어들인 객체의 타입이 SequenceNo 구현체라면
            // 일련 번호를 객체 식별 번호로 사용한다는 것이기 때문에
            // 목록에 저장된 객체 중에서 가장 큰 일련 번호를 알아내서 클래스의 스태틱 필드에 설정해야 함
            for (Class<?> type : elementType.getInterfaces()) {
                if (type == SequenceNo.class) {
                    initSeqNo(list, elementType);
                    break;
                }
            }

        } catch (Exception e) {
            System.out.printf("%s 파일 로딩 중 오류 발생\n", filename);
//            e.printStackTrace();
            userList = new ArrayList<>();
        }
    }
}
