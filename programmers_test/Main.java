import java.util.ArrayList;
import java.util.HashMap;

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

        HashMap<String,Integer> giftCount = hashMaker(friends, gifts);
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

    private int countGiveGet(String giveFriend, String getFriend, String[] gifts){
        int counter = 0;
        if(!giveFriend.equals(getFriend)){
            String giveSet = String.format("%s %s", giveFriend, getFriend);
            String getSet = String.format("%s %s", getFriend, giveSet);

            for(String gift : gifts){
                if(gift.equals(giveSet)){
                    counter++;
                }
                if (gift.equals(getSet)){
                    counter--;
                }
            }
        }

        return counter;
    }

    private HashMap<String, Integer> hashMaker(String[] friends, String[] gifts){
        HashMap<String, Integer> giftCount = new HashMap<>();
        for(String giveFriend : friends){
            for(String getFriend : friends){

                countGiveGet(giveFriend, getFriend, gifts);
            }
        }

        return giftCount;
    }

    private int result(HashMap<String, Integer> giftRate, HashMap<String, Integer> giftCount) {
        ArrayList<Integer> arr = new ArrayList<>();
//        for(String gift)
        return 0;
    }
}