package bitcamp.myapp.util;

public class Stack<E> extends LinkedList<E> {

  public static void main(String[] args) {
    Stack<String> s = new Stack<>();
    s.push("1111");
    s.push("2222");
    s.push("3333");

    System.out.println(s.pop());
    System.out.println(s.pop());
    System.out.println(s.pop());
    System.out.println(s.pop());

  }

  public void push(E value) {
    add(value);
  }

  public Object pop() {
    return remove(size() - 1);
  }

  public boolean isEmpty() {
    return size() == 0;
  }
}
