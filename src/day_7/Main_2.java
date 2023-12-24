package day_7;

import java.io.File;
import java.util.*;

public class Main_2 {

    private static HashMap<Character, Integer> charToValueMap = new HashMap<>();
    static {
        charToValueMap.put('2', 2);
        charToValueMap.put('3', 3);
        charToValueMap.put('4', 4);
        charToValueMap.put('5', 5);
        charToValueMap.put('6', 6);
        charToValueMap.put('7', 7);
        charToValueMap.put('8', 8);
        charToValueMap.put('9', 9);
        charToValueMap.put('T', 10);
        charToValueMap.put('J', 1); // jacks are wild but also the weakest!
        charToValueMap.put('Q', 11);
        charToValueMap.put('K', 12);
        charToValueMap.put('A', 13);
    }

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_7_data.txt"));

        HashMap<Hand_2, Integer> handToScoreMap = new HashMap<>();

        while(s.hasNext()) {
            String[] input = s.nextLine().split(" ");

            char[] handInput = input[0].toCharArray();
            int[] handOutput = new int[5];

            for(int i = 0; i < 5; i++) handOutput[i] = charToValueMap.get(handInput[i]);

            handToScoreMap.put(new Hand_2(handOutput), Integer.parseInt(input[1]));
        }

        List<Hand_2> hands = new ArrayList<>(handToScoreMap.keySet());
        Collections.sort(hands);

        int multi = 1;
        int total = 0;
        for(Hand_2 h : hands) {
            total += handToScoreMap.get(h)*multi++;
        }
        System.out.println(total);
    }

}
