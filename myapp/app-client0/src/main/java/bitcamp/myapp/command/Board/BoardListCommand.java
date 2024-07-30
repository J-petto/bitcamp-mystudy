package bitcamp.myapp.command.Board;

import bitcamp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

public class BoardListCommand implements Command {

    private BoardDao boardDao;

    public BoardListCommand(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void execute(String menuName) {
        try {
            System.out.printf("[%s]\n", menuName);

            System.out.println("번호 제목 작성일 조회수");

            for (Board board : boardDao.list()) {
                System.out.printf("%d %s %tY-%3$tm-%3$td %d\n",
                        board.getNo(), board.getTitle(), board.getCreatedDate(), board.getViewCount());
            }
        }catch (Exception e){
            System.out.println("보드 목록 조회 중 오류 발생");
        }

    }
}
