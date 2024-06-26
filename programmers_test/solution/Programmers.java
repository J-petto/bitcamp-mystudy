public class Programmers {
    public static void main(String[] args) {
        Solution0 s = new Solution0();
        System.out.println(s.solution("0010"));
        System.out.println(s.solution("854020"));
    }
}

class Solution0 {
    public String solution(String n_str) {
        return ""+Integer.parseInt(n_str);
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