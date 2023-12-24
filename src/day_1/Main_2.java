package day_1;

import java.io.File;
import java.util.Scanner;

public class Main_2 {

    public static void main(String[] args) {
        try {
            Scanner fileInput = new Scanner(new File("data/puzzle_1_data.txt"));

            int sum = 0;

            while(fileInput.hasNextLine()) {
                String line = fileInput.nextLine();
                line = line.replaceAll("zero", "zero0zero");
                line = line.replaceAll("one", "one1one");
                line = line.replaceAll("two", "two2two");
                line = line.replaceAll("three", "three3three");
                line = line.replaceAll("four", "four4four");
                line = line.replaceAll("five", "five5five");
                line = line.replaceAll("six", "six6six");
                line = line.replaceAll("seven", "seven7seven");
                line = line.replaceAll("eight", "eight8eight");
                line = line.replaceAll("nine", "nine9nine");
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
