package bitcamp.dao;

import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import java.util.List;

public interface ProjectDao {
    void insert(Project project);
    List<Project> list();
    Project findBy(int no);
    void update(Project project);
    void delete(int no);
}
