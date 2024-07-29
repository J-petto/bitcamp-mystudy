package bitcamp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Project;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListBoardDao implements BoardDao{
    private static int seqNo;

    private List<Board> boardList = new ArrayList<>();

    private String path;
    private String dataName;

    private static final String DEFAULT_DATA = "boards";

    public ListBoardDao(String path) {
        this(path, DEFAULT_DATA);
    }

    public ListBoardDao(String path, String dataName) {
        this.path = path;
        this.dataName = dataName;

        try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheet(dataName);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                Board board = new Board();
                board.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
                board.setTitle(row.getCell(1).getStringCellValue());
                board.setContent(row.getCell(2).getStringCellValue());
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    board.setCreatedDate(format.parse(row.getCell(3).getStringCellValue()));
                } catch (ParseException e) {
                    System.out.println("보드 날짜 포맷 적용 중 오류 발생");
                }
                board.setViewCount(Integer.parseInt(row.getCell(4).getStringCellValue()));

                boardList.add(board);

                seqNo = boardList.getLast().getNo();
            }
        }catch (IOException e){
            System.out.println("보드 로드 중 오류 발생");
        }

    }

    @Override
    public void insert(Board board) {

    }

    @Override
    public List<Board> list() {
        return List.of();
    }

    @Override
    public Board findBy(int no) {
        return null;
    }

    @Override
    public void update(Board board) {

    }

    @Override
    public void delete(int no) {

    }
}
