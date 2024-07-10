package bitcamp.myapp.util;

public class Stack extends LinkedList{
    public void push(Object value){
        add(value);
    }

    public Object pop(){
        return remove(size() -1);
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public static void main(String[] args) {
        Stack s = new Stack();
        s.push("Test");
        s.push("Test2");
        s.push("Test3");
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
    }
}
