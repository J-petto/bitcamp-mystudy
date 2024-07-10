import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(new String[]{"muzi", "ryan", "frodo", "neo"}, new String[]{"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"}));
        System.out.println(s.solution(new String[]{"joy", "brad", "alessandro", "conan", "david"}, new String[]{"alessandro brad", "alessandro joy", "alessandro conan", "david alessandro", "alessandro david"}));
    }
}

class Solution {
    public int solution(String[] friends, String[] gifts) {
        int answer = 0;

        // 선물 지수 계산하여 HashMap 설정 (Key = 이름 / value = 선물지수)
        HashMap<String, Integer> giftRate = new HashMap<>();
        for (String friend : friends){
            giftRate.put(friend, giftRate(friend, gifts));
        }

        HashMap<String, Integer> giftCount = new HashMap<>();
        hashMaker(friends, gifts, giftCount);
        for(String s : giftCount.keySet()){
            System.out.println(s + ": " + giftCount.get(s));
        }
        return answer;
    }

    private int giftRate(String friend, String[] gifts){
        int giveGifts = 0;
        int getGifts = 0;

        for (String gift : gifts){
            if(gift.split(" ")[0].equals(friend)){
                giveGifts++;
            }
            if(gift.split(" ")[1].equals(friend)){
                getGifts++;
            }
        }
        return giveGifts - getGifts;
    }

    private int test(String giftsSet, String[] gifts){
        int counter = 0;
        for(String gift : gifts){
            if(gift.equals(giftsSet)){
                counter++;
            }
        }
        return counter;
    }

    private void hashMaker(String[] friends, String[] gifts, HashMap<String, Integer> giftCount){
        for(String giveFriend : friends){
            for(String getFriend : friends){
                if(giveFriend.equals(getFriend)) continue;
                String giftsSet = String.format("%s %s", giveFriend, getFriend);
                giftCount.put(giftsSet, test(giftsSet, gifts));
            }
        }
    }
}