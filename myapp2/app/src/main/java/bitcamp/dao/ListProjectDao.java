package bitcamp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListProjectDao implements ProjectDao {
    private static int seqNo;

    private List<Project> projectList = new ArrayList<>();

    private String path;
    private String dataName;

    private static final String DEFAULT_DATA = "projects";

    public ListProjectDao(String path, UserDao userDao) {
        this(path, DEFAULT_DATA, userDao);
    }

    public ListProjectDao(String path, String dataName, UserDao userDao) {
        this.path = path;
        this.dataName = dataName;
        try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheet("projects");

            for(int i = 1; i <= sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);

                Project project = new Project();
                project.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
                project.setTitle(row.getCell(1).getStringCellValue());
                project.setDescription(row.getCell(2).getStringCellValue());
                project.setStartDate(row.getCell(3).getStringCellValue());
                project.setEndDate(row.getCell(4).getStringCellValue());

                String[] memberNo = row.getCell(5).getStringCellValue().split(",");
                for(String no : memberNo){
                    User user = userDao.findBy(Integer.parseInt(no));
                    if(user != null){
                        project.getMembers().add(user);
                    }
                }

                projectList.add(project);

                seqNo = projectList.getLast().getNo();
            }
        } catch (IOException e) {
            System.out.println("Project 정보 로딩 중 오류 발생");
        }
    }

    @Override
    public void insert(Project project) {

    }

    @Override
    public List<Project> list() {
        return List.of();
    }

    @Override
    public Project findBy(int no) {
        return null;
    }

    @Override
    public void update(Project project) {

    }

    @Override
    public void delete(int no) {

    }
}
