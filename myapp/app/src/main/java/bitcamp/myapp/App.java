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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class App {

    MenuGroup mainMenu = new MenuGroup("메인");

    HelpCommand helpCommand;
    HistoryCommand historyCommand;

    List<User> userList;
    List<Project> projectList;
    List<Board> boardList;

    public App() {
        userList = new ArrayList<>();
        projectList = new LinkedList<>();
        boardList = new LinkedList<>();

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
            loadData();
            mainMenu.execute();
        } catch (Exception ex) {
            System.out.println("실행 오류");
        } finally {
            saveData();
        }

        System.out.println("종료합니다.");

        Prompt.close();
    }

    private void loadData(){
        loadUsers();
        loadProjects();
        loadBoards();
        System.out.println("데이터를 로딩했습니다.");
    }

    private void loadUsers() {
        try(FileInputStream in = new FileInputStream("user.data")) {
            // User 데이터 개수 : 파일에서 2바이트를 읽음
            int userLength = (in.read() << 8) | in.read();

            int maxUserNo = 0;
            for(int i = 0; i < userLength; i++){
                // 한 개의 User 데이터 바이트 배열 크기 : 파일에서  2바이트 읽음
                int len = (in.read() << 8) | in.read();
                // 한 개의 User 데이터 바이트 배열 : 위에서 지정한 갯수만큼 바이트 배열 읽음
                byte[] bytes = new byte[len];
                in.read(bytes);

                // User 바이트 배열을 가지고 User 객체 생성
                User user = User.valueOf(bytes);
                userList.add(user);

                if(user.getNo() > maxUserNo){
                    maxUserNo = user.getNo();
                }
            }

            User.initSeqNo(maxUserNo);

        }catch (IOException e){
            System.out.println("회원 정보 로딩 중 오류 발생");

        }
    }

    private void loadProjects() {
        try(FileInputStream in = new FileInputStream("project.data")) {
            // User 데이터 개수 : 파일에서 2바이트를 읽음
            int projectLength = (in.read() << 8) | in.read();

            int maxProjectNo = 0;
            for(int i = 0; i < projectLength; i++){
                // 한 개의 User 데이터 바이트 배열 크기 : 파일에서  2바이트 읽음
                int len = (in.read() << 8) | in.read();
                // 한 개의 User 데이터 바이트 배열 : 위에서 지정한 갯수만큼 바이트 배열 읽음
                byte[] bytes = new byte[len];
                in.read(bytes);

                // User 바이트 배열을 가지고 User 객체 생성
                Project project = Project.valueOf(bytes);
                projectList.add(project);

                if(project.getNo() > maxProjectNo){
                    maxProjectNo = project.getNo();
                }
            }
            Project.initSeqNo(maxProjectNo);

        }catch (IOException e){
            System.out.println("게시판 정보 로딩 중 오류 발생");
        }
    }

    private void loadBoards() {
        try(FileInputStream in = new FileInputStream("board.data")) {
            // User 데이터 개수 : 파일에서 2바이트를 읽음
            int boardLength = (in.read() << 8) | in.read();

            int maxBoardNo = 0;
            for(int i = 0; i < boardLength; i++){
                // 한 개의 User 데이터 바이트 배열 크기 : 파일에서  2바이트 읽음
                int len = (in.read() << 8) | in.read();
                // 한 개의 User 데이터 바이트 배열 : 위에서 지정한 갯수만큼 바이트 배열 읽음
                byte[] bytes = new byte[len];
                in.read(bytes);

                // User 바이트 배열을 가지고 User 객체 생성
                Board board = Board.valueOf(bytes);
                boardList.add(board);

                if(board.getNo() > maxBoardNo){
                    maxBoardNo = board.getNo();
                }
            }

            Board.initSeqNo(maxBoardNo);

        }catch (IOException e){
            System.out.println("게시판 정보 로딩 중 오류 발생");
        }
    }

    private void saveData(){
        saveUsers();
        saveProjects();
        saveBoards();
        System.out.println("데이터를 저장했습니다.");
    }

    private void saveUsers(){
        // try 시 괄호 안의 객체는 자동 close 해줌
        try (FileOutputStream out = new FileOutputStream("user.data")){
            // 몇 개의 데이터를 읽을지 알려주기 위해 저장 데이터의 개수 출력
            out.write(userList.size() >> 8);
            out.write(userList.size());

            for(User user : userList){
                byte[] bytes = user.getBytes();
                // user 객체에 저장된 값을 꺼내 파일로 출력
                // User 데이터의 바이트 배열 크기 출력
                // Why? 읽을 때 한 개 분량의 User 바이트 배열을 읽기 위해
                out.write(bytes.length >> 8);
                out.write(bytes.length);
                out.write(bytes);
            }
        }catch (IOException e){
            System.out.println("회원 정보 저장 중 오류 발생");
        }
    }

    private void saveProjects(){
        try (FileOutputStream out = new FileOutputStream("project.data")){
            out.write(projectList.size() >> 8);
            out.write(projectList.size());

            for (Project project : projectList) {
                byte[] bytes = project.getBytes();
                // user 객체에 저장된 값을 꺼내 파일로 출력
                out.write(bytes.length >> 8);
                out.write(bytes.length);
                out.write(bytes);
            }
        }catch (IOException e){
            System.out.println("게시판 정보 저장 중 오류 발생");
        }
    }

    private void saveBoards(){
        try (FileOutputStream out = new FileOutputStream("board.data")){
            out.write(boardList.size() >> 8);
            out.write(boardList.size());

            for (Board board : boardList) {
                byte[] bytes = board.getBytes();
                // user 객체에 저장된 값을 꺼내 파일로 출력
                out.write(bytes.length >> 8);
                out.write(bytes.length);
                out.write(bytes);
            }
        }catch (IOException e){
            System.out.println("게시판 정보 저장 중 오류 발생");
        }
    }
}
