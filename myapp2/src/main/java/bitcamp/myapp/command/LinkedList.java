package bitcamp.myapp.command;

public class LinkedList {
    Node first;
    Node last;
    int size;

    public void add(Object value){
        Node newNode = new Node(value);
        size++;
        if(first == null){
            last = first = newNode;
        }
        last.next = newNode;
        last = newNode;
    }

    public Object get(int index){
        if(index < 0 || index > size){
            return null;
        }
        Node cursor = first;
        int cursorIndex = 0;
        while (cursor != null){
            if(cursorIndex == index){
                return cursor.value;
            }
            cursor = cursor.next;
            cursorIndex++;
        }
        return null;
    }

    public Object remove(int index){
        Node deleteNode = null;
        size--;
        if(index == 0){
            first = first.next;
            if(first == null){
                last = null;
            }
        }

        Node cursor = first;
        int currentIndex = 0;

        while (cursor != null) {
            if (currentIndex == (index - 1)) {
                break;
            }
            cursor = cursor.next;
            currentIndex++;
        }

        deleteNode = cursor.next;
        cursor.next = cursor.next.next;

        if (cursor.next == null) {
            last = cursor;
        }

        return deleteNode.value;
    }

    public int size() {
        return size;
    }

    public Object[] toArray() {
        Object[] arr = new Object[size];

        Node node = first;
        for (int i = 0; i < size; i++) {
            arr[i] = node.value;
            node = node.next;
        }
        return arr;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < size; i++) {
            if (get(i) == obj) {
                return i;
            }
        }
        return -1;
    }
}
