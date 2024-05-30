// 실습
// - 컴파일하고 실행하라.
//
// 결과 및 설명
// - 실행되지않고 오류발생 됨
// - error messege : Main method not found in class Test3, please define the main method as: public static void main(String[] args) or a JavaFX application class must extend javafx.application.Application
// - JVM은 'main' 메소드를 진입점(entry point)으로 사용한다. 그러기에 반드시 public static void main(String[] ~)이 정의된 메소드가 있어야한다.

// - TMI : 추가적인 정보로 뒤에 뜨는 JavaFX~의 오류는 만약 JavaFX 애플리케이션을 작성 중일때 애플리케이션 클래스는 'javafx.application.Application' 클래스를 상속해야하고 start(Stage primaryStage)라는 메소드를 구현해야한다.
class Test3 {
}
