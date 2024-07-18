package bitcamp.menu;

public class TestMenu {
    public static void main(String[] args) {
        MenuGroup root = new MenuGroup("메인");

        MenuGroup file = new MenuGroup("파일");
        root.add(file);

        MenuGroup add = new MenuGroup("새로 만들기");
        file.add(add);

        MenuGroup edit = new MenuGroup("편집");
        root.add(edit);

        MenuGroup help = new MenuGroup("도움말");
        root.add(help);

        root.execute();
    }
}
