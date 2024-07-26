package bitcamp.myapp.command.Board;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

import java.util.Map;

public class BoardViewCommand implements Command {

    private BoardDao boardDao;

    public BoardViewCommand(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void execute(String menuName) {
        try {
            System.out.printf("[%s]\n", menuName);

            int boardNo = Prompt.inputInt("게시글 번호?");

            Board board = boardDao.findBy(boardNo);
            if (board == null) {
                System.out.println("없는 게시글입니다.");
                return;
            }

            board.setViewCount(board.getViewCount() + 1);
            System.out.printf("제목: %s\n", board.getTitle());
            System.out.printf("내용: %s\n", board.getContent());
            System.out.printf("작성일: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS\n", board.getCreatedDate());
            System.out.printf("조회수: %d\n", board.getViewCount());

        } catch (Exception e) {
            System.out.println("보드 조회 중 오류 발생");
        }

    }
}
