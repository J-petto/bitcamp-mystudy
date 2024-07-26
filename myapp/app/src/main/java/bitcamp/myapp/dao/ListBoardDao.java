package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBoardDao implements BoardDao{

    private int seqNo;

    List<Board> boardList = new ArrayList<>();

    String path;
    String dataName;


    private static final String DEFAULT_DATA_NAME = "boards";

    public ListBoardDao(String path){
        this(path, DEFAULT_DATA_NAME);
    }

    public ListBoardDao(String path, String dataName){
        this.path = path;
        this.dataName = dataName;

        try(XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheet(dataName);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                try {
                    Row row = sheet.getRow(i);

                    Board board = new Board();
                    board.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
                    board.setTitle(row.getCell(1).getStringCellValue());
                    board.setContent(row.getCell(2).getStringCellValue());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    board.setCreatedDate(formatter.parse(row.getCell(3).getStringCellValue()));
                    board.setViewCount(Integer.parseInt(row.getCell(4).getStringCellValue()));

                    boardList.add(board);
                } catch (Exception e) {
                    System.out.printf("%s 번 보드의 날짜 데이터 형식이 맞지 않습니다.");
                }
            }
            seqNo = boardList.getLast().getNo();

        } catch (IOException e) {
            System.out.println("보드 데이터 로딩 중 오류 발생");
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        try(FileInputStream in = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(in)) {

            int sheetIndex = workbook.getSheetIndex(dataName);
            if (sheetIndex != -1) {
                workbook.removeSheetAt(sheetIndex);
            }

            XSSFSheet sheet = workbook.createSheet(dataName);

            String[] cellHeaders = {"no", "title", "content", "create_date", "view_count"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < cellHeaders.length; i++) {
                headerRow.createCell(i).setCellValue(cellHeaders[i]);
            }

            int rowNo = 1;
            for (Board board : boardList) {
                Row dataRow = sheet.createRow(rowNo++);
                dataRow.createCell(0).setCellValue(String.valueOf(board.getNo()));
                dataRow.createCell(1).setCellValue(board.getTitle());
                dataRow.createCell(2).setCellValue(board.getContent());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dataRow.createCell(3).setCellValue(formatter.format(board.getCreatedDate()));
                dataRow.createCell(4).setCellValue(String.valueOf(board.getViewCount()));
            }

            in.close();

            try (FileOutputStream out = new FileOutputStream(path)) {
                workbook.write(out);
            }
        }
    }


    @Override
    public boolean insert(Board board) throws Exception {
        board.setNo(++seqNo);
        boardList.add(board);
        return true;
    }

    @Override
    public List<Board> list() throws Exception {
        return boardList;
    }

    @Override
    public Board findBy(int no) throws Exception {
        for(Board board : boardList){
            if(board.getNo() == no){
                return board;
            }
        }
        return null;
    }

    @Override
    public boolean update(Board board) throws Exception {
        int index = boardList.indexOf(board);
        if(index == -1){
            return false;
        }
        boardList.set(index, board);
        return true;
    }

    @Override
    public boolean delete(int no) throws Exception {
        Board board = findBy(no);
        if(board == null){
            return false;
        }
        boardList.remove(board);
        return true;
    }
}
