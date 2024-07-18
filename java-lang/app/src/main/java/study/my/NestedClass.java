package study.my;

public class NestedClass {
  static class A {
    static int aA;
  }

  class B {

  }

  static void m1() {
    static int a;
    static class M1A {

    }
    int a2;
    class M1B {

    }
  }

  void m2() {
    static int a3;
    static class M2A {

    }
    int a4;
    class M2B {

    }
  }
}


class Test {
  public static void main(String[] args) {
    NestedClass.A na = new NestedClass.A();
    na.aA = 0;

    NestedClass.B nb = new NastedClass.B(); // 불가 바깥 클래스의 인스턴스가 아님
    NestedClass n = new NestedClass();
    nb = n.new B();


  }
}
