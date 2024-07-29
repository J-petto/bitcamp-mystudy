package bitcamp.dao;

import bitcamp.myapp.vo.Board;

import java.util.List;

public interface BoardDao {
    void insert(Board board);
    List<Board> list();
    Board findBy(int no);
    void update(Board board);
    void delete(int no);
}
