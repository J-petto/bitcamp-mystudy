package bitcamp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebInit /* implements WebApplicationInitializer */ {

  // @Override
  public void onStartup(ServletContext ctx) throws ServletException {

    System.out.println("WebInit.onStartup()호출됨!");

    // // DispatcherServlet의 IoC 컨테이너 생성
    // AnnotationConfigWebApplicationContext iocContainer =
    // new AnnotationConfigWebApplicationContext();
    // iocContainer.setServletContext(ctx);
    // iocContainer.register(bitcamp.AppConfig.class);
    //
    // // DispatcherServlet 객체 생성 및 등록
    // DispatcherServlet frontController = new DispatcherServlet(iocContainer);
    //
    // // Servlet 컨테이너에 등록
    // Dynamic options = ctx.addServlet("app", frontController);
    //
    // // Servlet 설정
    // options.addMapping("/app/*");
  }

}
