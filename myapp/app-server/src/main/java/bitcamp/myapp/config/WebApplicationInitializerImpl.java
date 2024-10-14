package bitcamp.myapp.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.io.File;

public class WebApplicationInitializerImpl extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // ContextLoaderListener의 IoC 컨테이너 설정
        // - DB 관련 객체 준비
        return new Class[] {RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // DispatcherServlet의 IoC 컨테이너 설정
        return new Class[] {AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/app/*"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // DispatcherServlet 객체에 대한 추가 설정
        registration.setMultipartConfig(new MultipartConfigElement( // 멀티 파트 설정
                new File("./temp/").getAbsolutePath(), // 업도드 파일을 임시 보관할 폴더
                1024 * 1024 * 60,
                1024 * 1024 * 100,
                1024 * 1024));
    }

    @Override
    protected Filter[] getServletFilters() {
        // DispatcherServlet에 붙일 필터 객체
        return new Filter[] {new CharacterEncodingFilter("UTF-8")};
    }
}
