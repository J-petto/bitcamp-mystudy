package bitcamp.myapp.util;

public class Queue extends LinkedList{
    public void offer(Object value){
        add(value);
    }

    public Object poll(){
        return remove(0);
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public static void main(String[] args) {
        Queue q = new Queue();
        q.offer("test1");
        q.offer("test2");
        q.offer("test3");
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
    }
}
