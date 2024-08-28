package bitcamp.myapp.listener;

import bitcamp.mybatis.SqlSessionFactoryProxy;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ResourceClearListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("요청 끝 - SqlSession.close()");
        // 응답을 완료하면 클라이언트 요청을 처리하기 위해 준비했던 자원을 해제 시킴
        ServletContext ctx = sre.getServletContext();
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ctx.getAttribute("sqlSession");
        ((SqlSessionFactoryProxy) sqlSessionFactory).clearSession();
    }
}
