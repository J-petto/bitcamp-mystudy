package study.lang.operator;

// 실습
// - 산술 연산자를 사용하여 연산을 수행한 후 결과를 확인하라.
//

public class Test02 {

  public static void main(String[] args) {
    byte b1 = 100;
    byte b2 = 20;
    byte b3 = 0;

    b3 = (byte) (b1 + b2);
    // byte의 값을 산술할 때 java는 int로 바꿔 연산하게됨 = 결과는 int다.
    // 즉, 그러니까 그냥 넣으려고 하면 안 들어감;
    // byte 뿐만이 아닌 char, short도 동일.
    System.out.println(b3);

    int r = b1 + b2;

    char c = 20;
    short s = 30;
    short r2 = (short) (c + s);

    int i1 = 21_0000_0000;
    int i2 = 21_0000_0000;
    // 두 값을 더하 int의 범위를 벗어난 값으로 오버플로우 발생됨.
    // 오버가 되는 값은 버림, 후 32비트의 첫 비트를 확인 시 1이되며 해당 값은 음수로 인식이됨.
    // 그래서 결과는 음수로 나오는 현상임.
    int i3 = i1 + i2;
    System.out.println(i3);

    // 이미 결과는 int로 오버플로우 되서 나옴.
    // 여기서 long에 넣어봤자 음수 값이 그대로 대입될 뿐
    long r3 = i1 + i2;
    System.out.println(r3);

    // long l1 = 22_0000_0000는 에러가 뜸
    // 뒤에 리터럴이 안붙으면 int로 인식 22억은 int의 값을 넘어갔으므로 에러가 뜨는 것
    long l1 = 22_0000_0000L;
    long l2 = 22_0000_0000L;
  }

}
