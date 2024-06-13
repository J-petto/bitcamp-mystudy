package study.lang.variable;

public class Test02 {
  public static void main(String[] args) {
    // - 문자 코드를 저장하는 방법을 확인하라
    // 코드를 완성하라.
    char c1 = 44032; // 변수에 '가' 문자의 코드를 정수 값으로 저장하라.
    char c2_1 = 0xAC00;
    char c2_2 = '\uAC00'; // 변수에 '가' 문자의 코드를 \u0000 으로 저장하라.
    char c3 = '가'; // 변수에 '가' 문자의 코드를 가장 쉬운 방법 으로 저장하라.

    System.out.println(c1);
    System.out.println(c2_1);
    System.out.println(c2_2);
    System.out.println(c3);

    String s;
    s = new String("aaa");

    String s2 = new String("aaa");

    System.out.println(s == s2); // 레퍼런스에 들어 있는 값을 비교

  }
}
