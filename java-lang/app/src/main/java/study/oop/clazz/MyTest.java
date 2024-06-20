package study.oop.clazz;

public class MyTest {
  public static void main(String[] args) {
    // Tests의 필드
    System.out.println(Tests.test1);

    Tests t = new Tests();
    System.out.println(t.test2);

    // Tests 클래스의 중첩 클래스 중 인스턴스 클래스 B의 필드
    System.out.println(Tests.B.test_B1);

    Tests.B b = t.new B();
    System.out.println(b.test_B2);

    // Tests 클래스의 중첩 클래스 중 스태틱 클래스 C의 필드
    System.out.println(Tests.C.test_C1);

    Tests.C c = new Tests.C();
    System.out.println(c.test_C2);
  }
}


class Tests {
  // nested class(중첩 클래스)
  static int test1 = 0; // static field
  int test2 = 99;

  class B {
    // non-static nested class
    static int test_B1 = 1; // static field
    int test_B2 = 2; // non-static field(= instance field)
  }
  static class C {
    // static nested class
    static int test_C1 = 3; // static field
    int test_C2 = 4; // non-static field
  }

  Object objA = new Object() {
    // anonymous class
    static int test_objA1 = 5;
    int test_objA2 = 6;
  };

  void m1() {
    class D {
      // local class
      static int test_D1 = 7;
      int test_D2 = 8;
    }
    Object objB = new Object() {
      // anonymous class
      static int test_objB1 = 9;
      int test_objB2 = 10;
    };
  }

  void m2() { /* m1 메서드 안에 있는 class는 사용 불가함 */ }
}


// package member class
class X {
  static int test_X1 = 11;
  int test_X2 = 12;
}

// static class Oh{
// 오직 중첩 클래스로만 존재함
// }
