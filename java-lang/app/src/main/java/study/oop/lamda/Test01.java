package study.oop.lamda;

public class Test01 {
  interface Player {
    void play();
  }

  public static void main(String[] args) {
    // 1. 일반
    class P implements Player {
      @Override
      public void play() {

        System.out.println("1");
      }
    }
    Player p = new P();
    p.play();
    // 2. 익명
    Player p1 = new Player() {
      @Override
      public void play() {

        System.out.println("1");
      }
    };
    p1.play();
    // 3. 익명 + 호출
    new Player() {
      @Override
      public void play() {

        System.out.println("1");
      }
    }.play();
    // 4. 람다
    Player p2 = () -> {
      System.out.println("1");
    };
    p2.play();
    // 5. 람다 + 중괄호
    Player p3 = () -> System.out.println("1");
    p3.play();
  }
}
