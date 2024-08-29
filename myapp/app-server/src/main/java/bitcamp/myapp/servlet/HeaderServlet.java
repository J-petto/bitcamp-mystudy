package bitcamp.myapp.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/header")
public class HeaderServlet extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <link rel='stylesheet' href='/css/common.css'>");
        out.println("        <title>프로젝트 관리 시스템</title>");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <header><a href='/'><img src='/images/home.png'/></a><span>프로젝트 관리 시스템</span></header>");
    }
}
