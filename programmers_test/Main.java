import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.toString(s.solution(new String[]{"and", "notad", "abcd"})));
        System.out.println(Arrays.toString(s.solution(new String[]{"there", "are", "no", "a", "ds"})));
    }
}

class Solution {
    public String[] solution(String[] strArr) {
        String[] answer = {};
        int count = 0;
        for(String s : strArr){
            if(s.contains("ad")){
                continue;
            }
            answer[count++] = s;
        }
        return strArr;
    }
}