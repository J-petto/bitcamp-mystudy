package bitcamp.myapp;

import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.myapp.command.Board.*;
import bitcamp.myapp.command.HelpCommand;
import bitcamp.myapp.command.HistoryCommand;
import bitcamp.myapp.command.Project.*;
import bitcamp.myapp.command.User.*;
import bitcamp.util.Prompt;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
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
        loadUsers();
        loadProjects();
        loadBoards();
        System.out.println("데이터를 로딩했습니다.");
    }

    private void saveData() {
        saveUsers();
        saveProjects();
        saveBoards();
        System.out.println("데이터를 저장했습니다.");
    }

    private void loadUsers() {
        try (BufferedReader in = new BufferedReader(new FileReader("user.json"))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null){
                stringBuilder.append(line);
            }

//            class MyTypeToken extends TypeToken<ArrayList<User>>{ }
//            TypeToken<ArrayList<User>> collectionType = new MyTypeToken();

//            TypeToken<ArrayList<User>> collectionType = new TypeToken<>(){};

            userList.addAll(new Gson()
                    .fromJson(stringBuilder.toString(), new TypeToken<ArrayList<User>>(){}));

            int maxUserNo = 0;
            for (User user : userList) {
                if (user.getNo() > maxUserNo) {
                    maxUserNo = user.getNo();
                }
            }

            User.initSeqNo(maxUserNo);

        } catch (IOException e) {
            System.out.println("회원 정보 로딩 중 오류 발생");
//            e.printStackTrace();
            userList = new ArrayList<>();
        }
    }

    private void loadProjects() {
        try (BufferedReader in = new BufferedReader(new FileReader("project.json"))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null){
                stringBuilder.append(line);
            }
            projectList.addAll(new Gson().fromJson(stringBuilder.toString(), new TypeToken<ArrayList<Project>>(){}));

            int maxProjectNo = 0;
            for (Project project : projectList) {
                if (project.getNo() > maxProjectNo) {
                    maxProjectNo = project.getNo();
                }
            }
            Project.initSeqNo(maxProjectNo);

        } catch (IOException e) {
            System.out.println("프로젝트 정보 로딩 중 오류 발생");
//            e.printStackTrace();
            projectList = new LinkedList<>();
        }
    }

    private void loadBoards() {
        try (BufferedReader in = new BufferedReader(new FileReader("board.json"))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null){
                stringBuilder.append(line);
            }

            boardList.addAll(new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create()
                    .fromJson(stringBuilder.toString(), new TypeToken<ArrayList<Board>>(){}));

            int maxBoardNo = 0;
            for (Board board : boardList) {
                if (board.getNo() > maxBoardNo) {
                    maxBoardNo = board.getNo();
                }
            }

            Board.initSeqNo(maxBoardNo);

        } catch (IOException e) {
            System.out.println("게시판 정보 로딩 중 오류 발생");
//            e.printStackTrace();
        }
    }

    private void saveUsers() {
        // try 시 괄호 안의 객체는 자동 close 해줌
        try (FileWriter out = new FileWriter("user.json")) {

            out.write(new Gson().toJson(userList));

        } catch (IOException e) {
            System.out.println("회원 정보 저장 중 오류 발생");
            e.printStackTrace();
        }
    }

    private void saveProjects() {
        try (FileWriter out = new FileWriter("project.json")) {

            out.write(new Gson().toJson(projectList));

        } catch (IOException e) {
            System.out.println("프로젝트 정보 저장 중 오류 발생");
            e.printStackTrace();
        }
    }

    private void saveBoards() {
        try (FileWriter out = new FileWriter("board.json")) {

            out.write(new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create()
                    .toJson(boardList));

        } catch (IOException e) {
            System.out.println("게시판 정보 저장 중 오류 발생");
            e.printStackTrace();
        }
    }
}
