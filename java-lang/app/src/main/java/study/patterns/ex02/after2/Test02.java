package study.patterns.ex02.after2;

public class Test02 {
  public static void main(String[] args) {
    ContentPrinter contentPrinter = new ContentPrinter();
    HeaderPrinter printer = new HeaderPrinter(contentPrinter, "공지사항");
    printer.print("Hi");
  }
}
