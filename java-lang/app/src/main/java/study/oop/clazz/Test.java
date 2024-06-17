package study.oop.clazz;

public class Test {
  static void m1(int a) {
    int b;
    b = 200;
  }

  void m2(int a) {
    // Test this;
    int b;
    b = 300;
  }
}


class TestGo {
  public static void main(String[] args) {
    Test.m1(100);

    Test test = new Test();
    test.m2(200);
    test.m1(200); // 에러는 뜨지않지만 헷갈리는 문법으로 툴에서 자동 경고 노출

    Math.abs(-100); // math : command + 클릭 -> static 붙은 아 메서드 확인(클래스 메서드 확인)
    String s; // String : command + 클릭 -> trim 메서드 확인(인스턴스 메서드 확인)
  }
}
