package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {

  boolean insert(Board board) throws Exception;

  // Map과 options를 사용하는 이유 : DB 종속성을 없애기 위해
  List<Board> list(Map<String, Object> options) throws Exception;

  Board findBy(int no) throws Exception;

  boolean update(Board board) throws Exception;

  boolean delete(int no) throws Exception;

  void updateViewCount(@Param("no") int boardNo, @Param("count") int count) throws Exception;

  int countAll() throws Exception;

  void insertFiles(Board board) throws Exception;

  AttachedFile getFile(int fileNo) throws Exception;

  boolean deleteFile(int fileNo) throws Exception;

  boolean deleteFiles(int boardNo) throws Exception;
}
