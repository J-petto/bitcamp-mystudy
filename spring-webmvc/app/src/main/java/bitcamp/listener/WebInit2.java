package bitcamp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.context.WebApplicationContext;

public class WebInit2 /* extends AbstractContextLoaderInitializer */ {

  // @Override
  protected WebApplicationContext createRootApplicationContext() {
    // ContextLoaderListener에 주입할 IoC 컨테이너를 리턴한다.
    // 만약 IoC 컨테이너를 리턴하지 않으면 ContextLoaderListener는 생성하지 않는다.
    return null;
  }

  // @Override
  public void onStartup(ServletContext ctx) throws ServletException {

    System.out.println("WebInit2.onStartup()호출됨!");

    // // 기존의 onStartup() 메서드의 기능은 그대로 수행한다.
    // super.onStartup(ctx);
    //
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
