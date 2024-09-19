package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.annotation.RequestParam;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/auth/form")
    public String form() throws Exception {
        return "/auth/form.jsp";
    }

    @RequestMapping("/auth/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam("saveEmail") boolean saveEmail,
                        HttpServletResponse res,
                        HttpSession httpSession) throws Exception {

        User user = userService.exists(email, password);
        if (user == null) {
            res.setHeader("Refresh", "2; url=login");
            return "/auth/fail.jsp";
        }

        Cookie cookie;
        if (saveEmail) {
            cookie = new Cookie("email", email);
            cookie.setMaxAge(60 * 60 * 24 * 7);
        } else {
            cookie = new Cookie("email", "test@test.com");
            cookie.setMaxAge(0);
        }
        res.addCookie(cookie);

        httpSession.setAttribute("loginUser", user);
        return "redirect:/";
    }

    @RequestMapping("/auth/logout")
    public String logout(HttpSession httpSession) throws Exception {
        httpSession.invalidate();
        return "redirect:/";
    }
}
