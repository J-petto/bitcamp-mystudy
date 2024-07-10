package bitcamp.myapp.util;

public abstract class AbstractList implements List {
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator iterator() {
        // anonymous class
        return new Iterator(){
            // 컴파일러가 바깥 클래스의 인스턴스 주소를 저장할 필드, 생성자 자동 생성
            private int cursor;

            @Override
            public boolean hasNext() {
                // 바깥 클래스의 인스턴스를 사용하려면
                // -> 바깥클래스명.this로 지정해야 함
                // return AbstractList.this.size()
                return cursor < size();
            }

            @Override
            public Object next() {
                // 바깥 클래스의 인스턴스를 사용하려면
                // -> 바깥클래스명.this로 지정해야 함
                // return AbstractList.this.get()
                return get(cursor++);
            }
        };
    }
}
