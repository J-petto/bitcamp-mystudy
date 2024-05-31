package study.lang.literal;

//실습
// - 리터럴을 사용하여 다음과 같이 출력되게 하라
// - 출력
// 100
// 3.14
// 가
// 가나다
// true

// 기본 자료형(Primitive Data Type)의 표현법(Literal)을 설명하시오
// 정수형 리터럴
// 1. byte(1) : -128 ~ 127 --------> 크기에 따라 리터럴을 구분하는 문법X
// 2. short(2) : -32768 ~ 32767 ---> 이하동문
// 3. int(4) : 42, 052(8진수), 0x2A(16진수), 0b101010(2진수)
// 약 -21억 ~ 21억
// 4. long(8) : 42L, 42l
// 약 -922경 ~ 922경
// 부동 소수점 리터럴
// 1. float(4) : 3.14f, 3.14F
// 2. double(8) : 3.14
// 문자형 리터럴
// char(2) : 'a', '1', '@'
// 문자열 리터럴
// string : "aaa", "123", "@"
// 논리형 리터럴
// boolean(int or byte) : true, false
// 특수 리터럴
// null

public class Test01 {
    public static void main(String[] args) {
        // 코드를 완성하라
        System.out.println(100); // 정수 리터럴 100
        System.out.println(3.14); // 부동소수점 리터럴 3.14
        System.out.println('가'); // 문자 리터럴 '가'
        System.out.println("가나다"); // 문자열 리터럴 "가나다"
        System.out.println(true); // 논리 리터럴 true
    }
}
