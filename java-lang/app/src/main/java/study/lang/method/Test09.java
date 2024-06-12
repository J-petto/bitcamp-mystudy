package study.lang.method;

import java.util.ArrayList;

public class Test09 {
  public static void main(String[] args) {
    int[] values = new int[] {100, 110, 120};

    String test = new String("테스트를 위한 문자열1");

    ArrayList<String> test2 = new ArrayList<String>();
    test2.add("테스트를 위한 문자열2");

    m1(values, test, test2);

    System.out.println("int[] : " + values[0]);
    System.out.println("String : " + test);
    System.out.println("Arrary : " + test2);
  }

  static void m1(int[] value, String test, ArrayList<String> test2) {
    value[0] = 200;
    test = "바꿔";
    test2.set(0, "변경");
  }
  // call by reference와 String
  // String 불변 객체이다.
  // ex. String a = "Hello";
  // a = a + "World"
  // 를 실행하면 Hello 문자열 자체를 변경하는 것이 아닌 새로운 문자열을 만들고 그 주소를 할당한다.
  // 즉 Hello는 가비지가 되고 HelloWorld라는 새로운 문자열이 생겨나는 것이다.
  // 그래서 String은 레퍼런스이지만 불변 객체로써 바뀔 수 없기 때문에 call by value로 동작이 된다.
}
