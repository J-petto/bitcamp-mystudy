package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ListProjectDao implements ProjectDao {
    private int seqNo;

    private List<Project> projectList = new ArrayList<>();

    private UserDao userDao;

    String path;
    String dataName;

    private static final String DEFAULT_DATA_NAME = "projects";


    public ListProjectDao(String path, UserDao userDao) {
        this(path, DEFAULT_DATA_NAME, userDao);
    }

    public ListProjectDao(String path, String dataName, UserDao userDao) {
        this.path = path;
        this.dataName = dataName;
        this.userDao = userDao;

        try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheet(dataName);

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
                    User user = userDao.findBy(Integer.parseInt(memberNo));
                    if (user != null) {
                        project.getMembers().add(user);
                    }
                }

                projectList.add(project);
            }

            seqNo = projectList.getLast().getNo();

        } catch (Exception e) {
            System.out.println("프로젝트 로딩 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        try (FileInputStream in = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(in)) {

            int sheetIndex = workbook.getSheetIndex(dataName);
            if (sheetIndex != -1) {
                workbook.removeSheetAt(sheetIndex);
            }
            XSSFSheet sheet = workbook.createSheet(dataName);

            String[] cellHeaders = {"no", "title", "description", "start_date", "end_date", "members"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < cellHeaders.length; i++) {
                headerRow.createCell(i).setCellValue(cellHeaders[i]);
            }

            int rowNo = 1;
            for (Project project : projectList) {
                Row dataRow = sheet.createRow(rowNo++);
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

            in.close();

            try (FileOutputStream out = new FileOutputStream(path)) {
                workbook.write(out);
            }
        }
    }

    @Override
    public boolean insert(Project project) throws Exception {
        project.setNo(++seqNo);
        projectList.add(project);
        return true;
    }

    @Override
    public List<Project> list() throws Exception {
        return projectList.stream().toList();
    }

    @Override
    public Project findBy(int no) throws Exception {
        for (Project project : projectList) {
            if (project.getNo() == no) {
                return project;
            }
        }
        return null;
    }

    @Override
    public boolean update(Project project) throws Exception {
        int index = projectList.indexOf(project);
        if (index == -1) {
            return false;
        }
        projectList.set(index, project);
        return true;
    }

    @Override
    public boolean delete(int no) throws Exception {
        Project project = findBy(no);
        if (project == null) {
            return false;
        }
        projectList.remove(project);
        return true;
    }
}
