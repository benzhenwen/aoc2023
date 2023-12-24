package day_4;

import day_3.Cell;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_4_data.txt"));

        int sum = 0;

        while(s.hasNextLine()) {
            String input = s.nextLine().split(":")[1];

            HashSet<String> winningValues = new HashSet<>();

            // assign winning values
            for (String win : input.split("\\|")[0].trim().split(" ")) {
                if(!win.isEmpty()) winningValues.add(win);
            }

            int add = 0;
            for (String num : input.split("\\|")[1].trim().split(" ")) {
                if(winningValues.contains(num)) {
                    if(add == 0) add = 1;
                    else add *= 2;
                }
            }
            sum += add;
        }
        System.out.println(sum);
    }

}
