package day_15;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_15_data.txt"));

        String[] lines = s.nextLine().split(",");

        long output = 0;

        for(String line : lines) {

            int hash = 0;

            for(char c : line.toCharArray()) {

                hash += (int) c;
                hash *= 17;
                hash %= 256;

            }

            output += hash;

        }

        System.out.println(output);

    }

}
