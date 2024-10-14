package bitcamp.myapp.controller;

import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {

  private UserService userService;
  private StorageService storageService;

  private String folderName = "user/";

  public UserController(UserService userService, StorageService storageService) {
    this.userService = userService;
    this.storageService = storageService;
  }

  @GetMapping("form")
  public String form() {
    return "user/form";
  }

  @PostMapping("add")
  public String add(User user, MultipartFile file) throws Exception {
    // 클라이언트가 보낸 파일을 저장할 때 다른 파일 이름과 충돌나지 않도록 임의로 지정
    String filename = UUID.randomUUID().toString();

    HashMap<String, Object> options = new HashMap<>();
    options.put(StorageService.CONTENT_TYPE, file.getContentType());
    storageService.upload(folderName + filename, file.getInputStream(), options);

    user.setPhoto(filename);

    userService.add(user);
    return "redirect:list";
  }

  @GetMapping("list")
  public ModelAndView list() throws Exception {
    List<User> list = userService.list();
    ModelAndView mv = new ModelAndView();
    mv.addObject("list", list);
    mv.setViewName("user/list");
    return mv;
  }

  @GetMapping("/view")
  public ModelAndView view(int no) throws Exception {
    User user = userService.get(no);
    ModelAndView mv = new ModelAndView();
    mv.addObject("user", user);
    mv.setViewName("user/view");
    return mv;
  }

  @PostMapping("update")
  public String update(User user, MultipartFile file) throws Exception {
    User old = userService.get(user.getNo());

    if(file != null && !file.isEmpty()) {
      storageService.delete(folderName + old.getPhoto());

      String filename = UUID.randomUUID().toString();

      HashMap<String, Object> options = new HashMap<>();
      options.put(StorageService.CONTENT_TYPE, file.getContentType());
      storageService.upload(folderName + filename, file.getInputStream(), options);

      user.setPhoto(filename);
    }else {
      user.setPhoto(old.getPhoto());
    }

    if (userService.update(user)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다!");
    }
  }

  @Transactional
  @GetMapping("delete")
  public String delete(int no) throws Exception {
    User old = userService.get(no);

    if (userService.delete(no)) {
      storageService.delete(folderName + old.getPhoto());

      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다.");
    }
  }
}
