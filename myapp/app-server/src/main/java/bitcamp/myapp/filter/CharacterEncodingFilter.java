package bitcamp.myapp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/*",
        initParams = @WebInitParam(name = "encoding", value = "UTF-8")
)
public class CharacterEncodingFilter implements Filter {

    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(filterConfig.getInitParameter("encoding") != null) {
            encoding = filterConfig.getInitParameter("encoding");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;

        if(httpReq.getMethod().equals("POST")) {
            request.setCharacterEncoding(this.encoding);
        }
        chain.doFilter(request, response); // 다음 필터 또는 서블릿 실행
    }
}
