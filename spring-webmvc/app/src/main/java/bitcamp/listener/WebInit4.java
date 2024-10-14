package bitcamp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@WebListener
public class WebInit4 extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    // ContextLoaderListener가 사용할 IoC 컨테이너를 만들 때 주입하는 Java Config 클래스를 리턴함.
    // 만약 IoC 설정 클래스를 리턴하지 않으면 IoC 컨테이너를 만들지 않을 것,
    // IoC 컨테이너를 만들지 않으면 ContextLoaderListenter도 생성하지 않을 것.
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    // DispatcherServlet이 사용할 IoC 컨테이너를 만들 때 주입하는 Java Config 클래스를 리턴함.
    return new Class<?>[] {bitcamp.AppConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app/*"};
  }

  @Override
  public void onStartup(ServletContext ctx) throws ServletException {
    // // 기존의 onStartup() 메서드의 기능은 그대로 수행한다.
    super.onStartup(ctx);
  }

}
