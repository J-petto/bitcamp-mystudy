package solution;

import java.util.Arrays;

public class Programmers {
    public static void main(String[] args) {
        Solution181835 s = new Solution181835();
        System.out.println(s.solution(new int[]{4, 6, 6, 4, 6, 6}));
        System.out.println(s.solution(new int[]{-1, 2, 5, 6, 3}));
    }
}

class Solution181835 {
    public int solution(int[] num_list) {
        int odd = 0;
        int even = 0;
        for (int i = 1; i < num_list.length; i++) {
            if (i % 2 == 1) {
                odd += num_list[i - 1];
            } else {
                even += num_list[i - 1];
            }
        }


        return odd == even ? odd : Math.max(odd, even);
    }

}

// 프로그래머스 코딩 기초 트레이닝 > 0 떼기 > 좌측에 있는 0을 떼세요.
// Integer.parseInt >> 문자열에 왼쪽부터 * 10을 해가는 방식으로 Integer을 만들어 간다.
// 그래서..

// System.out.println(s.solution("0010"));
// 0 -> result = (result * 10) + 0 = 0;
// 0 -> result = (result * 10) + 0 = 0;
// 1 -> result = (result * 10) + 1 = 1;
// 0 -> result = (result * 10) + 0 = 10;
// 이런식으로 좌측에 있는 0을 모두 뗄 수 있다.
// System.out.println(s.solution("854020"));
// public String solution(String n_str) {
//    return ""+Integer.parseInt(n_str);
// }