package bitcamp.myapp.dao.mysql;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ProjectDaoImpl implements ProjectDao {

    private Connection con;
    private UserDao userDao;

    public ProjectDaoImpl(Connection con, UserDao userDao) {
        this.con = con;
        this.userDao = userDao;
    }

    @Override
    public boolean insert(Project project) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(String.format("insert into myapp_projects(title, description, start_date, end_date, members) values ('%s', '%s', '%s', '%s', '%s')",
                    project.getTitle(), project.getDescription(), project.getStartDate(), project.getEndDate(), members(project)));

            return true;
        }
    }

    @Override
    public List<Project> list() throws Exception {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from myapp_projects order by project_id asc")) {

            ArrayList<Project> list = new ArrayList<>();

            while (rs.next()) { // select 실행 결과에서 1개의 레코드를 가져옴
                Project project = new Project();
                project.setNo(rs.getInt("project_id"));
                project.setTitle(rs.getString("title"));
                project.setDescription(rs.getString("description"));
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                list.add(project);
            }
            return list;
        }
    }

    @Override
    public Project findBy(int no) throws Exception {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(String.format("select * from myapp_projects where project_id='%d'", no))) {

            if (rs.next()) { // select 실행 결과에서 1개의 레코드를 가져옴
                Project project = new Project();
                project.setNo(rs.getInt("project_id"));
                project.setTitle(rs.getString("title"));
                project.setDescription(rs.getString("description"));
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                for (String memberNo : rs.getString("members").split(",")) {
                    if(memberNo.isEmpty()){
                        break;
                    }
                    User member = userDao.findBy(Integer.parseInt(memberNo));
                    project.getMembers().add(member);
                }
                return project;
            }
            return null;
        }
    }

    @Override
    public boolean update(Project project) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {

            int count = stmt.executeUpdate(String.format("update myapp_projects set" +
                            " title='%s', description='%s', start_date='%s', end_date='%s', members='%s' where project_id='%d'",
                    project.getTitle(), project.getDescription(), project.getStartDate(), project.getEndDate(), members(project), project.getNo()));

            return count > 0;
        }
    }

    @Override
    public boolean delete(int no) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {

            int count = stmt.executeUpdate(String.format("delete from myapp_projects where project_id='%d'", no));

            return count > 0;
        }
    }

    private String members(Project project){
        return project.getMembers().stream().map(user -> String.valueOf(user.getNo())).collect(Collectors.joining(","));
    }
}
