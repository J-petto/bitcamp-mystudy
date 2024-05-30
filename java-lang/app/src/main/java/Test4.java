//실습
// - 컴파일하고 실행하라.
//
// 결과 및 설명
// - 실행되지 않고 오류가 난다
// - error messege : Main method not found in class Test4, please define the main method as: public static void main(String[] args)
// - main 메서드가 public을 포함하고 있지 않기 때문
// - public이 붙지 않을 경우 main 메소드가 공개되어있지않은 상태라 JVM이 진입점(entry point)인 main 메서드를 찾지 못하기 때문이다.

public class Test4 {
    static void main(String[] a) {

    }
}
