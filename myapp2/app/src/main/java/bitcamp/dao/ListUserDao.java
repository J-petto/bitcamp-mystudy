package bitcamp.dao;

import bitcamp.myapp.vo.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ListUserDao implements UserDao {

    private static int seqNo;

    private List<User> userList = new ArrayList<>();

    private String path;
    private String dataName;

    private static final String DEFAULT_DATA = "users";

    public ListUserDao(String path) {
        this(path, DEFAULT_DATA);
    }

    public ListUserDao(String path, String dataName) {
        this.path = path;
        this.dataName = dataName;

        try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheet(dataName);

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

            seqNo = userList.getLast().getNo();
        } catch (IOException e) {
            System.out.println("User 정보 로딩 중 오류 발생");
        }
    }

    private void save() throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(path)){
            XSSFSheet sheet = workbook.createSheet(dataName);

            Row row = sheet.createRow(0);
            String[] header = {"no", "name", "email", "password", "tel"};
            for (int i = 0; i < header.length; i++) {
                row.createCell(i).setCellValue(header[i]);
            }

            for (int i = 0; i < userList.size(); i++) {
                row = sheet.createRow(i + 1);
                User user = userList.get(i);

                row.createCell(0).setCellValue(Integer.toString(user.getNo()));
                row.createCell(1).setCellValue(user.getName());
                row.createCell(2).setCellValue(user.getEmail());
                row.createCell(3).setCellValue(user.getPassword());
                row.createCell(4).setCellValue(user.getTel());
            }
        }
    }


    @Override
    public void insert(User user) {
        userList.add(user);
    }

    @Override
    public List<User> list() {
        return userList;
    }

    @Override
    public User findBy(int no) {
        for (User user : userList) {
            if (user.getNo() == no) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User user) {
        int index = userList.indexOf(user);
        if (index != -1) {
            userList.set(index, user);
        }
    }

    @Override
    public void delete(int no) {
        User user = findBy(no);
        if (user != null) {
            userList.remove(user);
        }
    }
}
