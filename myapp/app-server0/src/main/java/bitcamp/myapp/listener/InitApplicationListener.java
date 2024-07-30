package bitcamp.myapp.listener;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.dao.*;
import bitcamp.myapp.dao.skel.BoardDaoSkel;
import bitcamp.myapp.dao.skel.ProjectDaoSkel;
import bitcamp.myapp.dao.skel.UserDaoSkel;
import bitcamp.myapp.dao.stub.UserDaoStub;

import java.net.Socket;

public class InitApplicationListener implements ApplicationListener {

    UserDao userDao;
    BoardDao boardDao;
    ProjectDao projectDao;

    @Override
    public void onStart(ApplicationContext appCtx) throws Exception{
        userDao = new ListUserDao("data.xlsx");
        boardDao = new ListBoardDao("data.xlsx");
        projectDao = new ListProjectDao("data.xlsx", userDao);

        UserDaoSkel userDaoSkel = new UserDaoSkel(userDao);
        BoardDaoSkel boardDaoSkel = new BoardDaoSkel(boardDao);
        ProjectDaoSkel projectDaoSkel = new ProjectDaoSkel(projectDao);
        appCtx.setAttribute("userDaoSkel", userDaoSkel);
        appCtx.setAttribute("boardDaoSkel", boardDaoSkel);
        appCtx.setAttribute("projectDaoSkel", projectDaoSkel);
    }

    @Override
    public void onShutdown(ApplicationContext appCtx) throws Exception{
        try {
            ((ListUserDao) userDao).save();
            ((ListProjectDao) projectDao).save();
            ((ListBoardDao) boardDao).save();

            System.out.println("데이터를 저장했습니다.");
        } catch (Exception e) {
            System.out.println("데이터 저장 중 오류 발생");
            e.printStackTrace();
            System.out.println();
        }
    }
}
