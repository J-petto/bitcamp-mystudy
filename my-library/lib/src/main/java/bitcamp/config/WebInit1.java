package bitcamp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import bitcamp.WebInit;

public class WebInit1 implements WebInit {
  @Override
  public void start(ServletContext ctx) {
    // DispatcherServlet의 IoC 컨테이너 생성
    AnnotationConfigWebApplicationContext iocContainer =
        new AnnotationConfigWebApplicationContext();
    iocContainer.setServletContext(ctx);
    iocContainer.register(bitcamp.AppConfig.class);

    // DispatcherServlet 객체 생성 및 등록
    DispatcherServlet frontController = new DispatcherServlet(iocContainer);

    // Servlet 컨테이너에 등록
    Dynamic options = ctx.getServletContext().addServlet("app", frontController);

    // Servlet 설정
    options.addMapping("/app/*");
  }
}
