package bitcamp.menu;

import bitcamp.myapp.command.Command;

import java.util.Objects;

public class MenuItem extends AbstractMenu{

    Command command;

    public MenuItem(String title){
        super(title);
    }

    public MenuItem(String title, Command command) {
        super(title);
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        if(command != null){
            command.execute(title);
        }else {
            System.out.println(title);
        }
    }

}
