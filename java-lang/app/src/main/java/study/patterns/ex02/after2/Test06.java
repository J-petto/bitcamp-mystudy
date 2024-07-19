package study.patterns.ex02.after2;

public class Test06 {
  public static void main(String[] args) {
    ContentPrinter contentPrinter = new ContentPrinter();
    HeaderPrinter headerPrinter = new HeaderPrinter(contentPrinter, "편지");
    SignPrinter printer = new SignPrinter(headerPrinter, "홍길동");
    printer.print("Hi");
  }
}
