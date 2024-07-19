package study.patterns.ex02.after2;

public class Test03 {
  public static void main(String[] args) {
    ContentPrinter contentPrinter = new ContentPrinter();
    FooterPrinter printer = new FooterPrinter(contentPrinter, "비트캠프");
    printer.print("Hi");
  }
}
