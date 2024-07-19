package study.patterns.ex02.after2;

public class Test07 {
  public static void main(String[] args) {
    ContentPrinter contentPrinter = new ContentPrinter();
    HeaderPrinter headerPrinter = new HeaderPrinter(contentPrinter, "편지");
    FooterPrinter footerPrinter = new FooterPrinter(headerPrinter, "비트캠프");
    SignPrinter printer = new SignPrinter(footerPrinter, "홍길동");

    printer.print("Hi");
  }
}
