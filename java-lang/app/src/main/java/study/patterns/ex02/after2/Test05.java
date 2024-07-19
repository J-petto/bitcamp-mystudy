package study.patterns.ex02.after2;

public class Test05 {
  public static void main(String[] args) {
    ContentPrinter contentPrinter = new ContentPrinter();
    SignPrinter printer = new SignPrinter(contentPrinter, "홍길동");
    printer.print("Hi");
  }
}
