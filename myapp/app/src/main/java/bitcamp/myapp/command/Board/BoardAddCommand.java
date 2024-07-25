package bitcamp.myapp.command.Board;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BoardAddCommand implements Command {

    private BoardDao boardDao;

    public BoardAddCommand(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        Board board = new Board();
        board.setTitle(Prompt.input("제목?"));
        board.setContent(Prompt.input("내용?"));
        board.setCreatedDate(new Date());

        try {
            boardDao.insert(board);
        }catch (Exception e){
            System.out.println("보드 정보 추가 중 에러 발생");
        }
    }
}
