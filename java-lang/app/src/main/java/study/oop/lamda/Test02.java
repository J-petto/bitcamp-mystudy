package study.oop.lamda;

public class Test02 {
  interface Player {
    void play();
  }

  public static void main(String[] args) {
    // 1 일반
    class Test implements Player {
      @Override
      public void play() {
        // TODO Auto-generated method stub
        System.out.println("1");
      }
    }
    Player p = new Test();
    test(p);
    // 2 익명
    Player p2 = new Player() {
      @Override
      public void play() {
        // TODO Auto-generated method stub
        System.out.println("1");
      }
    };
    test(p2);
    // 익명 직접 대입
    test(new Player() {
      @Override
      public void play() {
        // TODO Auto-generated method stub
      }
    });
    // 람다
    Player p3 = () -> {
      System.out.println("1");
    };
    test(p3);
    // 람다 괄호삭제
    Player p4 = () -> System.out.println("1");
    test(p4);

    test(() -> System.out.println("1"));
  }

  static void test(Player player) {
    player.play();
  }
}
