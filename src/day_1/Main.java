package day_1;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner fileInput = new Scanner(new File("data/puzzle_1_data.txt"));

            int sum = 0;

            while(fileInput.hasNextLine()) {
                String line = fileInput.nextLine();
                sum += Integer.parseInt(getFirstDigit(line) + getFirstDigit(new StringBuilder(line).reverse().toString()));
            }

            System.out.println(sum);
        }
        catch(Exception e) {
            System.out.println(e);
            System.exit(-1);
        }

    }

    public static String getFirstDigit(String input) {
        for(char c : input.toCharArray()) {
            try {
                Integer.parseInt(String.valueOf(c));
                return String.valueOf(c);
            } catch (Exception e) {};
        }
        return null;
    }



}
