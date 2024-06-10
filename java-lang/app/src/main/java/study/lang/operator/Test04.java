package study.lang.operator;

// 실습
// - 산술 연산자를 사용하여 연산을 수행한 후 결과를 확인하라.
//

public class Test04 {

  public static void main(String[] args) {
    System.out.println(3 + 5 * 2);
    System.out.println(5 * 2 + 3);
    System.out.println((3 + 5) * 2);

    // 암시적 형변환 + 연산자 우선순위
    System.out.println(3.2f + 5 / 2L);
    System.out.println(3.2f + 5 / 2f);

    // 명시적 형변환 + 연산자 우선순위
    System.out.println(3.2f + (float) 5 / 2L);
  }

}
