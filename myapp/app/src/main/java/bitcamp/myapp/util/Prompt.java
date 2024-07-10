package bitcamp.myapp.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Prompt {

  static Scanner keyboardScanner = new Scanner(System.in);

  static Queue<String> inputQueue = new LinkedList<>();

  public static String input(String format, Object... args) {
    String promptTitle = String.format(format + " ", args);
    System.out.printf(format + " ", args);
    String input = keyboardScanner.nextLine();
    if(format.endsWith(">")){
      inputQueue.offer(promptTitle + input); // 최근 명령어를 최근 맨 뒤에 넣음
      if(inputQueue.size() > 20){
        inputQueue.poll();                           // 가장 오래된 값 삭제
      }
    }
    return input;
  }

  public static int inputInt(String format, Object... args) {
    return Integer.parseInt(input(format, args));
  }

  public static void printHistory(){
    System.out.println("[명령 내역]--------------");
      for (Object object : inputQueue) {
          System.out.println(object);
      }
    System.out.println("-------------------------");
  }

  public static void close() {
    keyboardScanner.close();
  }
}
