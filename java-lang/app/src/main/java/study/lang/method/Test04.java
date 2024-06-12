package study.lang.method;

public class Test04 {
  public static void main(String[] args) {
    // String messege = m1("홍길동", 20);
    System.out.println(m1("홍길동", 20));
  }

  static String m1(String name, int age) {
    // 1. String message = name + "(" + age + ")님 반가워요";
    // 2. String message = String.format("%s(%d)님 반가워요.", name, age);
    return String.format("%s(%d)님 반가워요.", name, age);
  }
}
