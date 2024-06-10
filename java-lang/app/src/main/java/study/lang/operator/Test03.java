package study.lang.operator;

// 실습
// - 산술 연산자를 사용하여 연산을 수행한 후 결과를 확인하라.
// 타입이 다르면 컴파일러는 같은 타입으로 자동 변환함 = implicit type conversion(casting)
// (byte, char, short) --> int --> long --> float --> double
// 개발자가 명시적으로 형변환하는 것 = explicit type conversion(casting)

public class Test03 {

  public static void main(String[] args) {
    byte b = 1;
    char c = 2;
    short s = 3;
    int i = 4;
    long l = 5;
    float f = 6.0f;
    double d = 7.0;

    int r = b + c + s;
    long r2 = i + l;
    // long r3 = f;

    // float은 4byte인데 8byte인 long은 들어갈 수 있음.
    // 대신 4byte를 넘어버리는 값이 들어오면 오버되는 값은 버린다.
    // 그래서 매우 유의해서 써야함.
    float r4 = l;
  }

}
