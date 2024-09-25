package bitcamp.myapp.controller;

import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user/form")
  public String form() {
    return "user/form";
  }

  @PostMapping("/user/add")
  public String add(User user) throws Exception {
    userService.add(user);
    return "redirect:list";
  }

  @GetMapping("/user/list")
  public ModelAndView list() throws Exception {
    List<User> list = userService.list();
    ModelAndView mv = new ModelAndView();
    mv.addObject("list", list);
    mv.setViewName("user/list");
    return mv;
  }

  @GetMapping("/user/view")
  public ModelAndView view(int no) throws Exception {
    User user = userService.get(no);
    ModelAndView mv = new ModelAndView();
    mv.addObject("user", user);
    mv.setViewName("user/view");
    return mv;
  }

  @PostMapping("/user/update")
  public String update(User user) throws Exception {
    if (userService.update(user)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다!");
    }
  }

  @GetMapping("/user/delete")
  public String delete(int no) throws Exception {
    if (userService.delete(no)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다.");
    }
  }
}
