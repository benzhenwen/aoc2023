package day_7;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * card values
 * 2 - 2
 * 3 - 3
 * 4 - 4
 * 5 - 5
 * 6 - 6
 * 7 - 7
 * 8 - 8
 * 9 - 9
 * T - 10
 * J - 11
 * Q - 12
 * K - 13
 * A - 14
 *
 * not the same for part 2!!
 */

public class Hand_2 implements Comparable<Hand_2> {

    private int[] cards;

    public Hand_2(int[] cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return Arrays.toString(cards);
    }

    @Override public int compareTo(Hand_2 other) {
        int selfType = this.getHandType();
        int otherType = other.getHandType();

        if(selfType != otherType) return selfType - otherType;

        for(int i = 0; i < 5; i++) {
            int selfVal = this.cards[i];
            int otherVal = other.cards[i];

            if(selfVal != otherVal) return selfVal - otherVal;
        }
        return 0;
    }

    /**
     * returns the hand type
     * 7 - Five of a kind
     * 6 - Four of a kind
     * 5 - Full house
     * 4 - Three of a kind
     * 3 - Two pair
     * 2 - One pair
     * 1 - High card
     * @return hand type
     */
    public int getHandType() {
        List<Integer> cardCounts = Arrays.asList(new Integer[13]);
        for(int i = 0; i < 13; i++) cardCounts.set(i, 0);

        for(int card : cards) cardCounts.set(card-1, cardCounts.get(card-1)+1);

        int jackCount = cardCounts.get(0);
        cardCounts.set(0, 0); // save jack count and wipe them out

        Collections.sort(cardCounts);
        Collections.reverse(cardCounts);

        cardCounts.set(0, cardCounts.get(0) + jackCount); // jacks count for the most common

        if(cardCounts.get(0) == 5)                           return 7; // five of a kind
        if(cardCounts.get(0) == 4)                           return 6; // four of a kind
        if(cardCounts.get(0) == 3 && cardCounts.get(1) == 2) return 5; // full house
        if(cardCounts.get(0) == 3)                           return 4; // three of a kind
        if(cardCounts.get(0) == 2 && cardCounts.get(1) == 2) return 3; // two pair
        if(cardCounts.get(0) == 2)                           return 2; // one pair
        else                                                 return 1; // high card;
    }

}
