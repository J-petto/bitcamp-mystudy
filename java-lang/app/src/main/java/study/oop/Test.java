package study.oop;

public class Test {
  public static void main(String[] args) {
    A obj = new C(); // = C obj = new C();
    obj.m1(); // m1() 메서드는 obj의 클래스부터 찾아 올라간다.
  }
}
