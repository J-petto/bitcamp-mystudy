package study.oop.clazz.util;

public class Calculator {
  private int result = 0;

  public void plus(int b) {
    this.result += b;
  }

  public void minus(int b) {
    this.result -= b;
  }

  public void multiple(int b) {
    this.result *= b;
  }

  public void divide(int b) {
    this.result /= b;
  }

  public int getResult() {
    return result;
  }

  public void clear() {
    this.result = 0;
  }
}
