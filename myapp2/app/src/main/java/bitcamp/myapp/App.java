package bitcamp.myapp;

import bitcamp.dao.*;
import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.myapp.command.HelpCommand;
import bitcamp.myapp.command.HistoryCommand;
import bitcamp.myapp.command.board.BoardAddCommand;
import bitcamp.myapp.command.board.BoardDeleteCommand;
import bitcamp.myapp.command.board.BoardListCommand;
import bitcamp.myapp.command.board.BoardUpdateCommand;
import bitcamp.myapp.command.board.BoardViewCommand;
import bitcamp.myapp.command.project.ProjectAddCommand;
import bitcamp.myapp.command.project.ProjectDeleteCommand;
import bitcamp.myapp.command.project.ProjectListCommand;
import bitcamp.myapp.command.project.ProjectMemberHandler;
import bitcamp.myapp.command.project.ProjectUpdateCommand;
import bitcamp.myapp.command.project.ProjectViewCommand;
import bitcamp.myapp.command.user.UserAddCommand;
import bitcamp.myapp.command.user.UserDeleteCommand;
import bitcamp.myapp.command.user.UserListCommand;
import bitcamp.myapp.command.user.UserUpdateCommand;
import bitcamp.myapp.command.user.UserViewCommand;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.SequenceNo;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class App {

    MenuGroup mainMenu = new MenuGroup("메인");

    UserDao userDao = new ListUserDao("data.xlsx");
    ProjectDao projectDao = new ListProjectDao("data.xlsx", userDao);
    BoardDao boardDao = new ListBoardDao("data.xlsx");
    List<Project> projectList = new LinkedList<>();
    List<Board> boardList = new LinkedList<>();

    public App() {

        loadData();

        MenuGroup userMenu = new MenuGroup("회원");
        userMenu.add(new MenuItem("등록", new UserAddCommand(userDao)));
        userMenu.add(new MenuItem("목록", new UserListCommand(userDao)));
        userMenu.add(new MenuItem("조회", new UserViewCommand(userDao)));
        userMenu.add(new MenuItem("변경", new UserUpdateCommand(userDao)));
        userMenu.add(new MenuItem("삭제", new UserDeleteCommand(userDao)));
        mainMenu.add(userMenu);

        MenuGroup projectMenu = new MenuGroup("프로젝트");
        ProjectMemberHandler memberHandler = new ProjectMemberHandler(userDao);
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

        mainMenu.add(new MenuItem("도움말", new HelpCommand()));
        mainMenu.add(new MenuItem("명령내역", new HistoryCommand()));

        mainMenu.setExitMenuTitle("종료");
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
            System.out.println("실행 오류!");
            ex.printStackTrace();

        } finally {
            saveData();
        }

        System.out.println("종료합니다.");

        Prompt.close();
    }

    private void loadData() {
        try{
            XSSFWorkbook workbook = new XSSFWorkbook("data.xlsx");

//            loadUsers(workbook);
            loadProjects(workbook);
            loadBoards(workbook);

            System.out.println("데이터를 로딩 했습니다.");

        }catch (IOException e){
            System.out.println("데이터 파일 로딩 중 오류 발생");
        }
    }

    private void loadBoards(XSSFWorkbook workbook) {

    }

    private void loadProjects(XSSFWorkbook workbook) {

    }

    private <E> void initSeqNo(List<E> list, Class<E> elementType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int maxSeqNo = 0;
        for (Object element : list) {
            SequenceNo seqElement = (SequenceNo) element;
            if (seqElement.getNo() > maxSeqNo) {
                maxSeqNo = seqElement.getNo();
            }
        }
        Method method = elementType.getMethod("initSeqNo", int.class);
        method.invoke(null, maxSeqNo);
    }

    private void saveData() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

//            saveUsers(workbook);
            saveProjects(workbook);
            saveBoards(workbook);

            try (FileOutputStream out = new FileOutputStream("data.xlsx")) {
                workbook.write(out);
            }
            System.out.println("데이터를 저장 했습니다.");

        } catch (IOException e) {
            System.out.println("정보 저장 중 오류 발생");
        }
    }

    private void saveBoards(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("boards");

        Row row = sheet.createRow(0);
        String[] header = {"no", "title", "content", "created_date", "view_count"};
        for (int i = 0; i < header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
        }

        for (int i = 0; i < boardList.size(); i++) {
            row = sheet.createRow(i + 1);
            Board board = boardList.get(i);
            row.createCell(0).setCellValue(Integer.toString(board.getNo()));
            row.createCell(1).setCellValue(board.getTitle());
            row.createCell(2).setCellValue(board.getContent());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            row.createCell(3).setCellValue(
                    format.format(board.getCreatedDate()));
            row.createCell(4).setCellValue(Integer.toString(board.getViewCount()));
        }
    }

    private void saveProjects(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("projects");

        Row row = sheet.createRow(0);
        String[] header = {"no", "title", "description", "start_date", "end_date", "member"};
        for (int i = 0; i < header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
        }

        for (int i = 0; i < projectList.size(); i++) {
            row = sheet.createRow(i + 1);
            Project project = projectList.get(i);
            row.createCell(0).setCellValue(Integer.toString(project.getNo()));
            row.createCell(1).setCellValue(project.getTitle());
            row.createCell(2).setCellValue(project.getDescription());
            row.createCell(3).setCellValue(project.getStartDate());
            row.createCell(4).setCellValue(project.getEndDate());
            StringBuilder stringBuilder = new StringBuilder();
            for (User member : project.getMembers()) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(member.getNo());
            }
            row.createCell(5).setCellValue(stringBuilder.toString());
        }
    }



}
