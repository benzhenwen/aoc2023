package day_24;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_24_data.txt"));

        ArrayList<Stone> stones = new ArrayList<>();
        while(s.hasNextLine()) {
            stones.add(new Stone(s.nextLine()));
        }

        int output = 0;
        for(int i = 0; i < stones.size()-1; i++) {
            for(int j = i+1; j < stones.size(); j++) {
                if(xyIntersectInBounds(stones.get(i), stones.get(j))) output++;
            }
        }

        System.out.println(output);

    }

    private static final double lower = 200000000000000d;
    private static final double upper = 400000000000000d;

    public static boolean xyIntersectInBounds(Stone a, Stone b) {
        double m1 = (double) a.velocity.y / a.velocity.x;
        double m2 = (double) b.velocity.y / b.velocity.x;
        double x1 = a.position.x;
        double x2 = b.position.x;
        double y1 = a.position.y;
        double y2 = b.position.y;

        double xIntersect = (m1*x1 - m2*x2 + y2-y1) / (m1-m2);
        if(!(xIntersect > lower && xIntersect < upper)) return false;

        double yIntersect = m1*xIntersect - m1*x1 + y1;
        if(!(yIntersect > lower && yIntersect < upper)) return false;

        if(a.velocity.x > 0 && xIntersect < a.position.x) return false;
        if(a.velocity.x < 0 && xIntersect > a.position.x) return false;
        if(b.velocity.x < 0 && xIntersect > b.position.x) return false;
        if(b.velocity.x > 0 && xIntersect < b.position.x) return false;
        if(a.velocity.y > 0 && yIntersect < a.position.y) return false;
        if(a.velocity.y < 0 && yIntersect > a.position.y) return false;
        if(b.velocity.y < 0 && yIntersect > b.position.y) return false;
        if(b.velocity.y > 0 && yIntersect < b.position.y) return false;

        return true;

    }
}
