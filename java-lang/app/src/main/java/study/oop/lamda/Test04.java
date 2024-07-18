package study.oop.lamda;

public class Test04 {
  interface InterestCalculator {
    double compute(int money);
  }

  // 1 일반 클래스 + 로컬 클래스의 특징 이용X
  static InterestCalculator create1(double rate) {
    class I implements InterestCalculator {
      double rate;

      public I(double rate) {
        this.rate = rate;
      }

      @Override
      public double compute(int money) {
        // TODO Auto-generated method stub
        return money + (money + rate);
      }
    }
    InterestCalculator i = new I(rate);
    return i;
  }

  // 2 일반 클래스 + 로컬 클래스의 특징 이용
  static InterestCalculator create2(double rate) {
    class I implements InterestCalculator {

      @Override
      public double compute(int money) {
        // TODO Auto-generated method stub
        return money + (money + rate);
      }
    }
    InterestCalculator i1 = new I();
    return i1;
  }

  // 3 익명 클래스
  static InterestCalculator create3(double rate) {
    InterestCalculator i2 = new InterestCalculator() {
      @Override
      public double compute(int money) {
        // TODO Auto-generated method stub
        return money + (money + rate);
      }
    };
    return i2;
  }

  // 4 익명 클래스 직접 대입
  static InterestCalculator create4(double rate) {
    return new InterestCalculator() {
      @Override
      public double compute(int money) {
        // TODO Auto-generated method stub
        return money + (money + rate);
      }
    };
  }

  // 5 람다
  static InterestCalculator create5(double rate) {
    InterestCalculator i2 = (money) -> {
      return money + (money + rate);
    };
    return i2;
  }

  // 6 람다 직접 대입
  static InterestCalculator create6(double rate) {

    return (money) -> money + (money + rate);
  }

  public static void main(String[] args) {
    InterestCalculator c1 = create1(0.035);
    System.out.println(c1.compute(1000_0000));

    System.out.println(create2(3.5).compute(1000_0000));
    System.out.println(create3(3.5).compute(1000_0000));
    System.out.println(create4(3.5).compute(1000_0000));
    System.out.println(create5(3.5).compute(1000_0000));
    System.out.println(create6(3.5).compute(1000_0000));
  }
}
