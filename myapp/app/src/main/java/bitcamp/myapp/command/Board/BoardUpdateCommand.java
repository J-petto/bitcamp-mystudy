package bitcamp.myapp.command.Board;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

import java.util.Map;

public class BoardUpdateCommand implements Command {

    private BoardDao boardDao;

    public BoardUpdateCommand(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        int boardNo = Prompt.inputInt("게시글 번호?");
        try {
            Board board = boardDao.findBy(boardNo);
            if (board == null) {
                System.out.println("없는 게시글입니다.");
                return;
            }

            board.setViewCount(board.getViewCount() + 1);
            board.setTitle(Prompt.input("제목(%s)?", board.getTitle()));
            board.setContent(Prompt.input("내용(%s)?", board.getContent()));

            if(boardDao.update(board)){
                System.out.println("변경 완료");
            }else {
                System.out.println("변경 실패");
            }

        }catch (Exception e){
            System.out.println("보드 변경 중 오류 발생");
        }

    }
}
