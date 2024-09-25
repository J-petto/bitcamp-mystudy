package bitcamp.myapp.controller;

import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

  @GetMapping("/home")
  public void home() { }

}
