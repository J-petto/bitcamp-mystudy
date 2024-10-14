package bitcamp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class WebInit3 /* extends AbstractDispatcherServletInitializer */ {

  // @Override
  protected WebApplicationContext createRootApplicationContext() {
    // ContextLoaderListener에 주입할 IoC 컨테이너를 리턴한다.
    // 만약 IoC 컨테이너를 리턴하지 않으면 ContextLoaderListener는 생성하지 않는다.
    return null;
  }

  // @Override
  protected WebApplicationContext createServletApplicationContext() {
    System.out.println("WebInit3.createServletApplicationContext()호출됨!");

    // DispatcherServlet의 IoC 컨테이너 생성
    AnnotationConfigWebApplicationContext iocContainer =
        new AnnotationConfigWebApplicationContext();
    // iocContainer.setServletContext(this.getS);
    iocContainer.register(bitcamp.AppConfig.class);

    return iocContainer;
  }

  // @Override
  protected String[] getServletMappings() {
    return new String[] {"/app/*"};
  }

  // @Override
  public void onStartup(ServletContext ctx) throws ServletException {
    // // 기존의 onStartup() 메서드의 기능은 그대로 수행한다.
    // super.onStartup(ctx);
  }

}
