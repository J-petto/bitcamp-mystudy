package study.patterns.ex01.step6;

public class Sedan extends Car {
  boolean sunroof;
  boolean auto;

  protected Sedan() {}

  @Override
  public String toString() {
    return "Sedan [sunroof=" + sunroof + ", auto=" + auto + ", maker=" + maker + ", model=" + model
        + ", cc=" + cc + "]";
  }

  @Override
  protected void start() {
    // TODO Auto-generated method stub
    System.out.printf("%s 시동\n", this.model);
  }

  @Override
  protected void run() {
    // TODO Auto-generated method stub
    System.out.printf("%s %s 달림\n", this.model, this.sunroof ? "썬루프 열고" : "썬루프 닫고");
  }

  @Override
  protected void stop() {
    // TODO Auto-generated method stub
    System.out.printf("%s 시동 종료\n", this.model);
  }

}
