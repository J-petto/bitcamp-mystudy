package study.oop.lamda;

public class Test05 {
  static class MyCalculator {
    public static int plus(int a, int b) {
      return a + b;
    }

    public static int minus(int a, int b) {
      return a - b;
    }

    public static int multiple(int a, int b) {
      return a * b;
    }

    public static int divide(int a, int b) {
      return a / b;
    }
  }

  interface Calculator {
    int compute(int x, int y);
  }

  public static void main(String[] args) {
    class My implements Calculator {
      @Override
      public int compute(int x, int y) {
        // TODO Auto-generated method stub
        return MyCalculator.plus(x, y);
      }
    }

    Calculator c = new My();
    Calculator c1 = (x, y) -> {
      return MyCalculator.plus(x, y);
    };
    Calculator c2 = (x, y) -> MyCalculator.plus(x, y);

    Calculator c3 = MyCalculator::plus; // staic method

    int result = c2.compute(0, 0);
  }
}
