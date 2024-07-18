import java.util.ArrayList;
import java.util.Collections;
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
        // 선물 지수 계산하여 HashMap 설정 (Key = 이름 / value = 선물지수)
        HashMap<String, Integer> giftRate = new HashMap<>();
        for (String friend : friends) {
            giftRate.put(friend, giftRate(friend, gifts));
        }

        HashMap<String, Integer> giftCount = hashMaker(friends, gifts);

        return result(giftRate, giftCount);
    }

    private int giftRate(String friend, String[] gifts) {
        int giveGifts = 0;
        int getGifts = 0;

        for (String gift : gifts) {
            if (gift.split(" ")[0].equals(friend)) {
                giveGifts++;
            }
            if (gift.split(" ")[1].equals(friend)) {
                getGifts++;
            }
        }
        return giveGifts - getGifts;
    }

    private int countGiveGet(String giveFriend, String getFriend, String[] gifts) {
        int counter = 0;
        if (!giveFriend.equals(getFriend)) {
            String set = String.format("%s %s", giveFriend, getFriend);
            for (String gift : gifts) {
                if (gift.equals(set)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private HashMap<String, Integer> hashMaker(String[] friends, String[] gifts) {
        HashMap<String, Integer> giftCount = new HashMap<>();
        for (String giveFriend : friends) {
            for (String getFriend : friends) {
                String set = String.format("%s %s", giveFriend, getFriend);
                giftCount.put(set, countGiveGet(giveFriend, getFriend, gifts));
            }
        }
        return giftCount;
    }

    private int result(HashMap<String, Integer> giftRate, HashMap<String, Integer> giftCount) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (String giver : giftRate.keySet()) {
            int give;
            int get;
            int count = 0;

            for (String getter : giftRate.keySet()) {
                if(giver.equals(getter)) continue;
                String giveFormat = String.format("%s %s", giver, getter);
                String getFormat = String.format("%s %s", getter, giver);

                give = giftCount.get(giveFormat);
                get = giftCount.get(getFormat);

                int result = give - get;

                if (result > 0) {
                    count++;
                }

                if (result == 0) {
                    count = giftRate.get(giver) > giftRate.get(getter) ? count + 1 : count;
                }
            }
            arr.add(count);
        }
        return Collections.max(arr);
    }
}