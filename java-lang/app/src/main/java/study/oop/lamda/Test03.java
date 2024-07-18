package study.oop.lamda;

public class Test03 {
  interface Calculator {
    int plus(int a, int b);
  }

  static void test(Calculator c) {
    System.out.println(c.plus(100, 200));
  }

  public static void main(String[] args) {
    // 1 일반 클래스
    class C implements Calculator {
      @Override
      public int plus(int a, int b) {
        return a + b;
      }
    }
    Calculator c = new C();
    test(c);
    // 2 익명 클래스
    Calculator c1 = new Calculator() {
      @Override
      public int plus(int a, int b) {
        return a + b;
      }
    };
    test(c1);
    // 3 익명 클래스 직접 대입
    test(new Calculator() {
      @Override
      public int plus(int a, int b) {
        return a + b;
      }
    });
    // 4 람다
    Calculator c3 = (int a, int b) -> {
      return a + b;
    };
    test(c3);
    // 람다 중괄호 생략
    Calculator c4 = (int a, int b) -> a + b;
    test(c4);
    // 람다 직접대입
    test((int a, int b) -> a + b);
  }
}
