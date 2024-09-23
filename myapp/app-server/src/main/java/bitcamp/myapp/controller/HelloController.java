package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.Controller;
import bitcamp.myapp.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public void hello(HttpServletResponse res) throws IOException {
        PrintWriter writer = res.getWriter();

        writer.println("Hello World!");
    }
}
