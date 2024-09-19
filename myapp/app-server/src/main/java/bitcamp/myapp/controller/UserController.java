package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.annotation.RequestParam;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/user/form")
  public String form() throws Exception {
      return "/user/form.jsp";
  }

  @RequestMapping("/user/add")
  public String add(@RequestParam("name") String name,
                    @RequestParam("email") String email,
                    @RequestParam("password") String password,
                    @RequestParam("tel") String tel) throws Exception {

      User user = new User();
      user.setName(name);
      user.setEmail(email);
      user.setPassword(password);
      user.setTel(tel);

      userService.add(user);
      return "redirect:list";
  }

  @RequestMapping("/user/list")
  public String list(Map<String, Object> map) throws Exception {
    List<User> list = userService.list();
    map.put("list", list);
    return "/user/list.jsp";
  }

  @RequestMapping("/user/view")
  public String view(@RequestParam("no") int no, Map<String, Object> map) throws Exception{
    User user = userService.get(no);
    map.put("user", user);
    return "/user/view.jsp";
  }

  @RequestMapping("/user/update")
  public String update(User user) throws Exception {
    if(userService.update(user)){
      return "redirect:list";
    }else {
      throw new Exception("없는 회원입니다.");
    }
  }

  @RequestMapping("/user/delete")
  public String delete(@RequestParam("no") int no) throws Exception {
    if(userService.delete(no)){
      return "redirect:list";
    }else {
      throw new Exception("없는 회원입니다.");
    }
  }

}
