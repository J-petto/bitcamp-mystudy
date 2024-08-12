package study.concurrent;

class TestThread extends Thread {
  @Override
  public void run() {
    // TODO Auto-generated method stub
    for (int i = 0; i < 100; i++) {
      System.out.printf("자식스레드---> %d\n", i);
    }
  }
}


public class Test2 {
  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      System.out.printf("===> %d\n", i);
    }
    System.out.print("------------------------");

    Thread t = new TestThread();
    t.start();

    for (int i = 0; i < 100; i++) {
      System.out.printf("~~~~~~~~~> %d\n", i);
    }
  }
}
