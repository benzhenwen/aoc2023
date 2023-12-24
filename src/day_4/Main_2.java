package day_4;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Main_2 {

    private static LinkedList<Integer> cardWinnings = new LinkedList<>();

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_4_data.txt"));

        // initialize cardWinnings so there is one of each card
        for(int i = 0; i < 214; i++) cardWinnings.add(1);

        int totalCards = 0;

        while(s.hasNextLine()) {
            String input = s.nextLine().split(":")[1];
            int winAmount = getCardWinAmount(input);

            // add cards
            int selfCardCount = cardWinnings.pop();
            totalCards += selfCardCount;
            for(int i = 0; i < winAmount; i++) {
                cardWinnings.set(i, cardWinnings.get(i)+selfCardCount);
            }
        }

        System.out.println(totalCards);
    }

    private static int getCardWinAmount(String card) {
        HashSet<String> winningValues = new HashSet<>();

        // assign winning values
        for (String win : card.split("\\|")[0].trim().split(" ")) {
            if(!win.isEmpty()) winningValues.add(win);
        }

        int add = 0;
        for (String num : card.split("\\|")[1].trim().split(" ")) {
            if(winningValues.contains(num)) {
                add++;
            }
        }
        return add;
    }

}
