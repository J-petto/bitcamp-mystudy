package bitcamp.myapp.service;

import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import java.util.List;

public interface ProjectService {
    void add(Project project) throws Exception;

    List<Project> list() throws Exception;

    Project get(int projectNo) throws Exception;

    boolean update(Project project) throws Exception;

    void deleteMembers(int no) throws Exception;

    void insertMembers(int no, List<User> members) throws Exception;

    boolean delete(int projectNo) throws Exception;
}
