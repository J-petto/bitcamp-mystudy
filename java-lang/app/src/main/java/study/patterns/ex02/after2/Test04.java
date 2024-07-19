package study.patterns.ex02.after2;

public class Test04 {
  public static void main(String[] args) {
    ContentPrinter contentPrinter = new ContentPrinter();
    HeaderPrinter headerPrinter = new HeaderPrinter(contentPrinter, "인사말");
    FooterPrinter printer = new FooterPrinter(headerPrinter, "비트캠프");
    printer.print("Hi");
  }
}
