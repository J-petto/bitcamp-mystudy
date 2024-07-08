package bitcamp.myapp.command;

import bitcamp.myapp.util.LinkedList;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Board;

import java.util.Date;

public class HelpCommand implements Command{

  String menuTitle;

  public HelpCommand(String menuTitle){
    this.menuTitle = menuTitle;
  }

  public void execute() {
    System.out.println("도움말입니다.");
  }

}
