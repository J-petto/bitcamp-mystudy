package study.oop.clazz;

import study.oop.clazz.vo.Score;

public class Test02 {
  // 연습: 클래스 문법을 데이터 타입을 정의하는 용도로 사용
  // 1) 클래스 사용전:
  // - 낱개 변수 사용
  // 2) 성적 데이터를 저장할 메모리를 새로 정의: Score 클래스
  // 3) 리팩토링: printScore() 메서드
  // 4) 리팩토링: compute() 메서드
  // 5) GRASP(General Responsibility Assignment Software Patterns) 패턴: compute() 멧서드 이동
  // 6) 인스턴스 메서드: compute() 메서드를 non-static 메서드로 전환
  // 7) 클래스를 역할에 따라 패키지로 분류: vo 패키지 생성 및 Score 클래스를 vo 패키지로 이동, import 문
  // 8) Score 멤버의 접근 제어 변경: public modifier
  // 9) 생성자 도입: Score() 생성자
  // 10) private과 getter 도입: sum, aver 필드는 private, 대신에 getSum(), getAver() 추가
  // 11) 국,영,수 점수 변경 후 합계 평균 자동 계산: kor, eng, math 필드는 private, setter/getter 추가
  // 12) 코딩의 일관성을 위해 다른 필드도 getter/setter로 접근하게 만든다.
  //
  public static void main(String[] args) {

    Score s1 = new Score("홍길동", 100, 90, 85);
    printScore(s1);

    Score s2 = new Score("임꺽정", 90, 80, 75);
    printScore(s2);

    Score s3 = new Score("유관순", 80, 70, 65);
    printScore(s3);
  }

  static void printScore(Score s) {
    System.out.printf("%s: %d, %d, %d, %d, %.1f\n", s.getName(), s.getKor(), s.getEng(),
        s.getMath(), s.getSum(), s.getAver());
  }
}
