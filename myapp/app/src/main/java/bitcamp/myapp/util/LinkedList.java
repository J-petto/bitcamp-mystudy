package bitcamp.myapp.util;

public class LinkedList extends AbstractList {
    private Node first;
    private Node last;

    @Override
    public void add(Object value){
        Node newNode = new Node(value);

        if(first == null){
            last = first = newNode;
        }else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public Object get(int index) {
        if (index < 0|| index >= size) {
            return null;
        }

        Node cursor = first;
        int currentIndex = 0;

        while (cursor != null) {
            if(currentIndex == index){
                return cursor.value;
            }
            cursor = cursor.next;
            currentIndex++;
        }
        return null;
    }

    @Override
    public Object remove(int index){
        if(index < 0 || index >= size){
            return null;
        }

        Node deletedNode = null;
        size--;

//        첫번째 노드 삭제
        if (index == 0){
            deletedNode = first;
            first = first.next;
            if(first == null){
                last = null;
            }
            return deletedNode.value;
        }

//        중간 노드 삭제
        Node cursor = first;
        int currentIndex = 0;

        while (cursor != null){
            if(currentIndex == (index - 1)){
                break;
            }
            cursor = cursor.next;
            currentIndex++;
        }
        deletedNode = cursor.next;
        cursor.next = cursor.next.next;

//        마지막 노드 삭제
        if(cursor.next == null){
            last = cursor;
        }
        return deletedNode.value;
    }

    @Override
    public int indexOf(Object value){
        Node cursor = first;
        int currentIndex = 0;

        while (cursor != null) {
            if(cursor.value.equals(value)){
                return currentIndex;
            }
            cursor = cursor.next;
            currentIndex++;
        }
        return -1;
    }

    @Override
    public Object[] toArray(){
        Object[] arr = new Object[size];

        Node cursor = first;
        for(int i = 0; i < size; i++){
            arr[i] = cursor.value;
            cursor = cursor.next;
        }
        return arr;
    }

    // 스태틱 중첩 클래스 (LinkedList에만 사용되기 때문에 static을 붙여 중첩 클래스로 만든다)
    // 인스턴스 멤버가 포함되는 클래스라면 인스턴스 클래스로 만들어라.
    private static class Node {
        Object value;
        Node next;

        public Node(Object value){
            this.value = value;
        }
    }
}
