package bitcamp.myapp.dao.mysql;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {

    private Connection con;

    public ProjectDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public boolean insert(Project project) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(
                    String.format("insert into myapp_projects(title, description, start_date, end_date) values ('%s', '%s', '%s', '%s')",
                    project.getTitle(), project.getDescription(), project.getStartDate(), project.getEndDate()),
                    Statement.RETURN_GENERATED_KEYS // insert 후 자동 생성된 PK 값 가져옴
            );

            ResultSet keyRS = stmt.getGeneratedKeys(); // 리턴한 ResultSet 받기

            keyRS.next(); // ResultSet에서 PK 가져옴(1개가 생성되었기에 1개...)

            int projectNo = keyRS.getInt(1);
            project.setNo(projectNo);
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
                createMemberList(project);
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
                            " title='%s', description='%s', start_date='%s', end_date='%s' where project_id='%d'",
                    project.getTitle(), project.getDescription(), project.getStartDate(), project.getEndDate(), project.getNo()));

            deleteMembers(project.getNo(), project.getMembers());

            return count > 0;
        }
    }

    @Override
    public boolean delete(int no) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(String.format("delete from myapp_project_members where project_id=%d", no));

            int count = stmt.executeUpdate(String.format("delete from myapp_projects where project_id='%d'", no));
            return count > 0;
        }
    }

//    private String members(Project project) {
//        return project.getMembers().stream().map(user -> String.valueOf(user.getNo())).collect(Collectors.joining(","));
//    }

    @Override
    public void createMemberList(Project project) {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(String.format("select m.user_id, u.name, u.email, u.pwd, u.tel, m.project_id" +
                     " from myapp_project_members m" +
                     " join myapp_users u on m.user_id = u.user_id" +
                     " where m.project_id = %d;", project.getNo()))) {

            while (rs.next()) {
                User user = new User();
                user.setNo(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("pwd"));
                user.setTel(rs.getString("tel"));

                project.getMembers().add(user);
            }
        } catch (SQLException e) {
            System.out.println("멤버 리스트 생성 중 오류 발생");
            e.printStackTrace();
        }
    }

    @Override
    public void insertMembers(int projectNo, List<User> members) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {
            for (User member : members) {
                stmt.executeUpdate(String.format("insert into myapp_project_members(user_id, project_id) values ('%s', '%s')", member.getNo(), projectNo));
            }
        }
    }

    @Override
    public void deleteMembers(int projectNo, List<User> members){
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(String.format("delete from myapp_project_members where project_id=%d", projectNo));
            for(User member : members){
                stmt.executeUpdate(String.format("insert into myapp_project_members(user_id, project_id) values ('%s', '%s')", member.getNo(), projectNo));
            }
        } catch (SQLException e) {
            System.out.println("멤버 삭제 중 오류 발생");
        }
    }
}
