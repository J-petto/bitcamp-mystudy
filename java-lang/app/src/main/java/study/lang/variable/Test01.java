package study.lang.variable;

public class Test01 {
  public static void main(String[] args) {
    // - primitive type의 변수를 선언하라.
    // - 각 변수 최소값, 최대값을 할당하라.
    // - 각 변수에 최소값, 최대값 범위를 벗어나는 값을 넣은 후 오류를 확인하라.
    // - ex. byte b1 = -128;
    // byte b2 = 127

    byte b1 = -128;
    byte b2 = 127;

    short s1 = -32768;
    short s2 = 32767;

    int i1 = -2147483648;
    int i2 = 2147483647;

    long l1 = -9223372036854775808L;
    long l2 = 9223372036854775807L;

    // 부동소수점은 범위 초과 시 오류가 나지않고 값이 잘려서 나옴
    float f1 = -9876.543f;
    float f2 = 9876.543f;

    double d1 = -987654323456.789;
    double d2 = 98765432.3456789;

    System.out.println(f1);
    System.out.println(f2);
    System.out.println(d1);
    System.out.println(d2);

    char c1 = 0;
    char c2 = 65535;

    boolean bool1 = false;
    boolean bool2 = true;

    // System.out.println((int) Character.MAX_VALUE);
    // System.out.println((int) Character.MIN_VALUE);
  }
}
