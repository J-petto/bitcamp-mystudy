package bitcamp.myapp.listener;

import bitcamp.myapp.controller.user.*;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.DaoFactory;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.service.*;
import bitcamp.mybatis.SqlSessionFactoryProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebListener // 서블릿 컨테이너에 이 클래스를 배치하는 태그.
public class ContextLoaderListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // 서블릿 컨테이너가 실행될 때 호출됨
    try {
      System.out.println("...서비스 관련 객체 준비...");
      InputStream inputStream = Resources.getResourceAsStream("config/mybatis-config.xml");
      SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
      SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

      SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

      DaoFactory daoFactory = new DaoFactory(sqlSessionFactoryProxy);

      UserDao userDao = daoFactory.createObject(UserDao.class);
      BoardDao boardDao = daoFactory.createObject(BoardDao.class);
      ProjectDao projectDao = daoFactory.createObject(ProjectDao.class);

      UserService userService = new DefaultUserService(userDao, sqlSessionFactoryProxy);
      BoardService boardService = new DefaultBoardService(boardDao, sqlSessionFactoryProxy);
      ProjectService projectService = new DefaultProjectService(projectDao, sqlSessionFactoryProxy);

      ServletContext ctx = sce.getServletContext();
      ctx.setAttribute("userService", userService);
      ctx.setAttribute("boardService", boardService);
      ctx.setAttribute("projectService", projectService);

      ctx.setAttribute("userDao", userDao);
      ctx.setAttribute("boardDao", boardDao);
      ctx.setAttribute("projectDao", projectDao);
      ctx.setAttribute("sqlSession", sqlSessionFactoryProxy);

      Map<String, Object> controllerMap = new HashMap<>();
      controllerMap.put("/user/list", new UserListController(userService));
      controllerMap.put("/user/view", new UserViewController(userService));
      controllerMap.put("/user/add", new UserAddController(userService));
      controllerMap.put("/user/update", new UserUpdateController(userService));
      controllerMap.put("/user/delete", new UserDeleteController(userService));

      ctx.setAttribute("controllerMap", controllerMap);

    }catch (Exception e){
      System.out.println("서비스 객체 준비 중 오류 발생");
      e.printStackTrace();
    }
  }
}
