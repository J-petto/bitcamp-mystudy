package bitcamp.myapp.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Prompt {

  static Scanner keyboardScanner = new Scanner(System.in);
  // Queue는 java.util.Collection을 상속 받았음.
  // LinkedList는 Deque를 구현함 Deque는 Queue를 상속받기 때문에 또 상위인 Collection을 상속 받음
  // 결국 Queue와 LinkedList는 Collection의 자식 클래스로 포함이 됨
  static Queue<String> inputQueue = new LinkedList<>();

  public static String input(String format, Object... args) {
    String promptTitle = String.format(format + " ", args);
    System.out.print(promptTitle);
    
    String input = keyboardScanner.nextLine();
    if (format.endsWith(">")) {
      inputQueue.offer(promptTitle + input); // 최근 명령어를 큐의 맨 뒤에 넣는다.
      if (inputQueue.size() > 20) {
        inputQueue.poll(); // 가장 오래된 값을 큐에서 꺼낸다.
      }
    }
    return input;
  }

  public static int inputInt(String format, Object... args) {
    return Integer.parseInt(input(format, args));
  }

  public static void close() {
    keyboardScanner.close();
  }

  public static void printHistory() {
    System.out.println("[명령 내역]----------------");
      for (Object object : inputQueue) {
          System.out.println(object);
      }
    System.out.println("------------------------ 끝");
  }
}
