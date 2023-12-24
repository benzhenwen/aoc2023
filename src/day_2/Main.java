package day_2;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner fileInput = new Scanner(new File("data/puzzle_2_data.txt"));

        int gameID = 0;

        int sum = 0;

        while(fileInput.hasNextLine()) {
            gameID++;

            String line = fileInput.nextLine().split(":")[1];

            boolean isValid = true;

            out:
            for(String hand : line.split(";")) {
                for(String numberType : hand.split(",")) {
                    String[] numColor = numberType.trim().split(" ");
                    int num = Integer.parseInt(numColor[0]);
                    switch(numColor[1]) {
                        case "red":
                            if(num > 12) {
                                isValid = false;
                                break out;
                            }
                            break;
                        case "green":
                            if(num > 13) {
                                isValid = false;
                                break out;
                            }
                            break;
                        case "blue":
                            if(num > 14) {
                                isValid = false;
                                break out;
                            }
                            break;
                        default:
                            System.out.println("no color?!");
                            System.exit(-1);
                    }
                }
            }
            // made it past all checks
            if(isValid) {
                sum += gameID;
            }
        }

        System.out.println(sum);
    }

}
