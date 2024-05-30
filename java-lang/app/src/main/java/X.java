// 실습
// - 이 파일을 컴파일 했을 때 나온 결과를 확인하고 그 이유를 설명하라.
//
// 결과 및 해설
// - 컴파일이 되지않으며 error messege : class X is public, should be declared in a file named X.java 가 노출됨
// - 자바는 '하나'의 소스 파일에 '하나'의 public 클래스를 선언 할 수 있는데 public의 클래스 이름은 파일의 이름이랑 "반드시" 동일해야한다.
// - 현재는 자동으로 바뀌었지만 원래 Test2.java 파일 이름을 가졌었던 이 경우
// - 1. 파일 이름을 public class X { } 와 동일한 X.java 로 바꾼다.
// - 2. 클래스 이름을 Test2.java 와 동일한 public class Test2 { }로 바꾸어야한다.

public class X {
}

class Y {
}

class Z {
}
