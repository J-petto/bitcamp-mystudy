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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
        try {
            XSSFWorkbook workbook = new XSSFWorkbook("data.xlsx");

            loadUsers(workbook);
            loadProjects(workbook);
            loadBoards(workbook);

        } catch (Exception e) {
            System.out.println("데이터 로드 중 오류 발생");
            e.printStackTrace();
        }

        System.out.println("데이터를 로딩했습니다.");
    }

    private void loadBoards(XSSFWorkbook workbook){

            XSSFSheet sheet = workbook.getSheet("boards");

            for(int i = 1; i <= sheet.getLastRowNum(); i++){
                try{
                    Row row = sheet.getRow(i);

                    Board board = new Board();
                    board.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
                    board.setTitle(row.getCell(1).getStringCellValue());
                    board.setContent(row.getCell(2).getStringCellValue());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    board.setCreatedDate(formatter.parse(row.getCell(3).getStringCellValue()));
                    board.setViewCount(Integer.parseInt(row.getCell(4).getStringCellValue()));

                    boardList.add(board);
                }catch (Exception e){
                    System.out.printf("%s 번 보드의 날짜 데이터 형식이 맞지 않습니다.");
                }
            }

        try {
            initSeqNo(boardList, Board.class);
        }catch (Exception e){
            System.out.println("보드 일련 번호 초기화 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }


    private void loadProjects(XSSFWorkbook workbook) {
        try {
            XSSFSheet sheet = workbook.getSheet("projects");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                Project project = new Project();
                project.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
                project.setTitle(row.getCell(1).getStringCellValue());
                project.setDescription(row.getCell(2).getStringCellValue());
                project.setStartDate(row.getCell(3).getStringCellValue());
                project.setEndDate(row.getCell(4).getStringCellValue());

                String[] membersNo = row.getCell(5).getStringCellValue().split(",");
                for (String memberNo : membersNo) {
                    User user = findUserByNo(Integer.parseInt(memberNo));
                    if(user != null){
                        project.getMembers().add(user);
                    }
                }
                projectList.add(project);
            }

            for (Class<?> type : Project.class.getInterfaces()) {
                if (type == SequenceNo.class) {
                    initSeqNo(projectList, Project.class);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("프로젝트 로딩 중 오류가 발생했습니다.");
        }
    }

    private User findUserByNo(int no){
        for (User user : userList) {
            if (user.getNo() == no) {
                return user;
            }
        }
        return null;
    }

    private void loadUsers(XSSFWorkbook workbook) {
        try {
            XSSFSheet sheet = workbook.getSheet("users");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                User user = new User();

                user.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
                user.setName(row.getCell(1).getStringCellValue());
                user.setEmail(row.getCell(2).getStringCellValue());
                user.setPassword(row.getCell(3).getStringCellValue());
                user.setTel(row.getCell(4).getStringCellValue());

                userList.add(user);
            }

            for (Class<?> type : User.class.getInterfaces()) {
                if (type == SequenceNo.class) {
                    initSeqNo(userList, User.class);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("User 데이터 로딩 중 오류가 발생했습니다.");
        }
    }

    private void saveData() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            saveUsers(workbook);
            saveProjects(workbook);
            saveBoards(workbook);

            try (FileOutputStream out = new FileOutputStream("data.xlsx")) {
                workbook.write(out);
            }

            System.out.println("데이터를 저장했습니다.");

        } catch (Exception e) {
            System.out.println("데이터 저장 중 오류 발생");
            e.printStackTrace();
        }
    }

    private void saveProjects(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("projects");

        String[] cellHeaders = {"no", "title", "description", "start_date", "end_date", "members"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < cellHeaders.length; i++) {
            headerRow.createCell(i).setCellValue(cellHeaders[i]);
        }

        for (int i = 0; i < projectList.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            Project project = projectList.get(i);
            dataRow.createCell(0).setCellValue(String.valueOf(project.getNo()));
            dataRow.createCell(1).setCellValue(project.getTitle());
            dataRow.createCell(2).setCellValue(project.getDescription());
            dataRow.createCell(3).setCellValue(project.getStartDate());
            dataRow.createCell(4).setCellValue(project.getEndDate());

            StringBuilder stringBuilder = new StringBuilder();
            for (User member : project.getMembers()) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(member.getNo());
            }
            dataRow.createCell(5).setCellValue(stringBuilder.toString());
        }
    }

    private void saveBoards(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("boards");

        String[] cellHeaders = {"no", "title", "content", "create_date", "view_count"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < cellHeaders.length; i++) {
            headerRow.createCell(i).setCellValue(cellHeaders[i]);
        }

        for (int i = 0; i < boardList.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            Board board = boardList.get(i);
            dataRow.createCell(0).setCellValue(String.valueOf(board.getNo()));
            dataRow.createCell(1).setCellValue(board.getTitle());
            dataRow.createCell(2).setCellValue(board.getContent());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dataRow.createCell(3).setCellValue(formatter.format(board.getCreatedDate()));
            dataRow.createCell(4).setCellValue(String.valueOf(board.getViewCount()));
        }
    }

    private void saveUsers(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("users");

        String[] cellHeaders = {"no", "name", "email", "password", "tel"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < cellHeaders.length; i++) {
            headerRow.createCell(i).setCellValue(cellHeaders[i]);
        }

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(String.valueOf(user.getNo()));
            dataRow.createCell(1).setCellValue(user.getName());
            dataRow.createCell(2).setCellValue(user.getEmail());
            dataRow.createCell(3).setCellValue(user.getPassword());
            dataRow.createCell(4).setCellValue(user.getTel());
        }
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
