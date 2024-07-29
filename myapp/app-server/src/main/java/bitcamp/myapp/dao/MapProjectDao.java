package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapProjectDao implements ProjectDao {
    private int seqNo;

    private Map<Integer, Project> projectMap = new HashMap<>();
    private List<Integer> projectNoList = new ArrayList<>();

    private UserDao userDao;

    String path;
    String dataName;

    private static final String DEFAULT_DATA_NAME = "projects";


    public MapProjectDao(String path, UserDao userDao){
        this(path, DEFAULT_DATA_NAME, userDao);
    }

    public MapProjectDao(String path, String dataName, UserDao userDao){
        this.path = path;
        this.dataName = dataName;
        this.userDao = userDao;

        try (XSSFWorkbook workbook = new XSSFWorkbook(path)){
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
                projectMap.put(project.getNo(), project);
                projectNoList.add(project.getNo());
            }

            seqNo = projectNoList.getLast();

        } catch (Exception e) {
            System.out.println("프로젝트 로딩 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        try (FileInputStream in = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(in)){

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
            for (Integer no : projectNoList) {
                Project project = projectMap.get(no);
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
        projectMap.put(project.getNo(), project);
        projectNoList.add(project.getNo());
        return true;
    }

    @Override
    public List<Project> list() throws Exception {
        ArrayList<Project> projects = new ArrayList<>();
        for (Integer userNo : projectNoList) {
            projects.add(projectMap.get(userNo));
        }
        return projects;
    }

    @Override
    public Project findBy(int no) throws Exception {
        return projectMap.get(no);
    }

    @Override
    public boolean update(Project project) throws Exception {
        if(!projectMap.containsKey(project.getNo())){
            return false;
        }
        projectMap.put(project.getNo(), project);
        return true;
    }

    @Override
    public boolean delete(int no) throws Exception {
        if(projectMap.remove(no) == null){
            return false;
        }
        projectNoList.remove(Integer.valueOf(no));
        return true;
    }
}
