
package bitcamp.myapp;

public class App {
    static String[] menus = {"회원", "팀", "프로젝트", "게시판", "도움말", "종료"};
    static String[][] subMenus = {
            {"등록a", "목록", "조회", "변경", "삭제"},
            {"등록b", "목록", "조회", "변경", "삭제"},
            {"등록c", "목록", "조회", "변경", "삭제"},
            {"등록d", "목록", "조회", "변경", "삭제"},
    };

    public static void main(String[] args) {
        new App().processMain();
    }

    void processMain(){
        printMenus();

        int menuNo;
        while (true){
            String input = Prompt.input("메인>");
            if(input.equals("menu")){
                printMenus();
            }
            try {
                menuNo = Integer.parseInt(input);
                String menuTitle = getMenuTitle(menuNo, menus);
                if(menuTitle == null){
                    System.out.println("메뉴 번호가 옳지 않습니다.");
                }else if(menuTitle.equals("종료")){
                    System.out.println("종료합니다");
                    break;
                }else {
                    printSubMenus(menuTitle, subMenus[menuNo]);
                    while (true){
                        input = Prompt.input("메인/%s>", menuTitle);
                        if(input.equals("menu")){
                            printSubMenus(menuTitle, subMenus[menuNo]);
                        }
                        if(input.equals("9")){
                            break;
                        }
                        try {
                            menuNo = Integer.parseInt(input);
                            String subMenuTitle = getMenuTitle(menuNo, subMenus[menuNo]);
                            System.out.println(subMenuTitle);
                        }catch (NumberFormatException e){
                            System.out.println("메뉴 번호가 아닙니다.");
                        }
                    }

                }
            }catch (NumberFormatException e){
                System.out.println("메뉴 번호가 아닙니다.");
            }
        }
    }

    void printMenus(){
        String bold = "\033[1m";
        String red = "\033[31m";
        String endANSI = "\033[0m";

        String line = "----------------------------------------------";
        String title = "팀 프로젝트 관리 시스템";

        System.out.println(bold + line + endANSI);
        System.out.printf("%s[%s]%s\n", bold, title, endANSI);
        for (int i = 0; i < menus.length; i++) {
            if(menus[i].equals("종료")){
                System.out.printf("%s%s%d. %s %s\n", bold, red, i+1, menus[i], endANSI);
            }else {
                System.out.printf("%d. %s\n", i + 1, menus[i]);
            }
        }
        System.out.println(bold + line + endANSI);
    }

    void printSubMenus(String menuTitle, String[] menus){
        System.out.printf("[%s]\n", menuTitle);
        for(int i = 0; i < menus.length; i++){
            System.out.printf("%d. %s\n", i + 1, menus[i]);
        }
        System.out.println("9. 이전");
    }

    boolean isValidateMenu(int menuNo, String[] menus){
        return menuNo < 1 || menuNo > menus.length;
    }

    String getMenuTitle(int menuNo, String[] menus){
        if(isValidateMenu(menuNo, menus)){
            return null;
        }
        return menus[menuNo -1];
    }


}
