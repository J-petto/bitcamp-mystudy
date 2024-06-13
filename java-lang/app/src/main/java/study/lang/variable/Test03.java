package study.lang.variable;

public class Test03 {
  public static void main(String[] args) {

    int[][] arr = new int[3][];
    arr[0] = new int[] {1, 2, 3};
    arr[1] = new int[] {1, 2, 3, 4};
    arr[2] = new int[] {1, 2, 3, 4, 5};

    System.out.println(arr.length);
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + ": ");
      for (int j = 0; j < arr[i].length; j++) {
        System.out.print(arr[i][j] + ", ");
      }
    }

    int[][] arr2 = new int[3][4];
    // 이건 이런뜻이다...
    // arr[0] = new int[4];
    // arr[1] = new int[4];
    // arr[2] = new int[4];

    // 그래서 이렇게 쓰면 저 new in[4]가 할당된 주소값이 날아가면서 가비지로 전환 됨
    arr2[0] = new int[] {1, 2, 3};
    arr2[1] = new int[] {1, 2, 3, 4};
    arr2[2] = new int[] {1, 2, 3, 4, 5};

    System.out.println(arr2.length);
    for (int i = 0; i < arr2.length; i++) {
      System.out.print(arr2[i] + ": ");
      for (int j = 0; j < arr2[i].length; j++) {
        System.out.print(arr2[i][j] + ", ");
      }
    }
  }
}
