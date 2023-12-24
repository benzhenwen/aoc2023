package day_2;

import java.io.File;
import java.util.Scanner;

public class Main_2 {

    public static void main(String[] args) throws Exception {
        Scanner fileInput = new Scanner(new File("data/puzzle_2_data.txt"));

        int sum = 0;

        while(fileInput.hasNextLine()) {
            String line = fileInput.nextLine().split(":")[1];

            boolean isValid = true;

            int maxRed = 0;
            int maxGreen = 0;
            int maxBlue = 0;

            out:
            for(String hand : line.split(";")) {
                for(String numberType : hand.split(",")) {
                    String[] numColor = numberType.trim().split(" ");
                    int num = Integer.parseInt(numColor[0]);
                    switch(numColor[1]) {
                        case "red":
                            if(num > maxRed) maxRed = num;
                            break;
                        case "green":
                            if(num > maxGreen) maxGreen = num;
                            break;
                        case "blue":
                            if(num > maxBlue) maxBlue = num;
                            break;
                        default:
                            System.out.println("no color?!");
                            System.exit(-1);
                    }
                }
            }
            sum += maxRed * maxGreen * maxBlue;
        }
        System.out.println(sum);
    }

}
