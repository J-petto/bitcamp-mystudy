package study.oop.clazz;

import study.oop.clazz.util.Calculator;

// 연습 : 클래스 문법을 메서드 분류하는 용도로 사용
// 1) 메서드 분류
// 2) static 필드 사용
// 3) non-static 필드 사용
// 4) 인스턴스 메서드 사용
// 5) private, getter 사용
// 6) 인스턴스 메서드 사용 : clear()
//
public class Test01 {
  public static void main(String[] args) {

    Calculator c = new Calculator();

    c.plus(2);
    c.plus(3);
    c.minus(1);
    c.multiple(7);
    c.divide(3);

    System.out.printf("result = %d\n", c.getResult());

    c.clear();
    System.out.printf("result = %d\n", c.getResult());
  }
}
