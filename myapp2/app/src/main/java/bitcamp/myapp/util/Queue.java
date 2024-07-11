package bitcamp.myapp.util;

public class Queue<E> extends LinkedList<E> {

  public static void main(String[] args) {
    Queue<String> q = new Queue<>();
    q.offer("111");
    q.offer("222");
    q.offer("333");

    System.out.println(q.poll());
    System.out.println(q.poll());
    System.out.println(q.poll());
    System.out.println(q.poll());
  }

  public void offer(E value) {
    add(value);
  }

  public Object poll() {
    return remove(0);
  }

  public boolean isEmpty() {
    return size() == 0;
  }
}
