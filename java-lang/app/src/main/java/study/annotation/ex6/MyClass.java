// 애노테이션 프로퍼티 타입
package study.annotation.ex6;

// @MyAnnotation은 타입(인터페이스와 클래스)에만 붙일 수 있다.
@MyAnnotation // OK!
public class MyClass {

  // @MyAnnotation
  int i; // 컴파일 오류!

  // @MyAnnotation
  public void m(/* @MyAnnotation */ int p) {
    /* @MyAnnotation */ int a;
  }

}


@MyAnnotation
interface MyInterface {

  // @MyAnnotation
  void m();
}


@MyAnnotation
enum MyEnum {

}
