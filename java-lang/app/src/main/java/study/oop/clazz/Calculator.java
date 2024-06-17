package study.oop.clazz;

public class Calculator {
  private int result = 0;

  int plus(int b) {
    return this.result += b;
  }

  int minus(int b) {
    return this.result -= b;
  }

  int multiple(int b) {
    return this.result *= b;
  }

  int divide(int b) {
    return this.result /= b;
  }

  int getResult() {
    return this.result;
  }

  void clear() {
    this.result = 0;
  }
}
