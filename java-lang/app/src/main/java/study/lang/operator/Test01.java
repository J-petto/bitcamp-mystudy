package study.lang.operator;

// 실습
// - 산술 연산자를 사용하여 연산을 수행한 후 결과를 확인하라.
// - 정수 + 정수 = 정수
// - 부동소수점 + 부동소수점 = 부동소수점
// - 정수 + 부동소수점 (부동소수점 + 정수) = 부동소수점
// - 연산은 같은 타입만 가능 -> 타입이 다르면 타입을 맞춤
// -> 타입을 맞추는 규칙 : 정수 --> 부동소수점

public class Test01 {

  public static void main(String[] args) {
    System.out.println(5 + 2);
    System.out.println(5 - 2);
    System.out.println(5 * 2);
    System.out.println(5 / 2);
    System.out.println(5 % 2);


    // 부동소수점의 연산
    float f = 100.0f;
    System.out.println(5.0 + 2.0);
    System.out.println(5.0 - 2.0);
    System.out.println(5.0 * 2.0);
    System.out.println(5.0 / 2.0);
    System.out.println(5.0 % 2.0);

    // 정수와 부동소수점의 연산 결과
    System.out.println(5 / 2.0);
    System.out.println(5.0 / 2);


  }

}
