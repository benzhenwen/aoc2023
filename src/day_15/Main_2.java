package day_15;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_15_data.txt"));

        String[] lines = s.nextLine().split(",");

        ArrayList<String>[] boxes = new ArrayList[256];
        for(int i = 0; i < boxes.length; i++) boxes[i] = new ArrayList<>();
        HashMap<String, Integer> focalLengths = new HashMap<>();

        for(String line : lines) {

            if(line.contains("-")) {
                String label = line.split("-")[0];
                boxes[hash(label)].remove(label);
            }
            else if(line.contains("=")) {
                String label = line.split("=")[0];
                int h = hash(label);
                if(!boxes[h].contains(label)) boxes[h].add(label);
                focalLengths.put(label, Integer.valueOf(line.split("=")[1]));
            }

        }

        long output = 0;

        int boxNumber = 1;
        for(ArrayList<String> box : boxes) {
            int slotNumber = 1;
            for(String label : box) {
                output += boxNumber * slotNumber * focalLengths.get(label);
                slotNumber++;
            }
            boxNumber++;
        }

        System.out.println(output);


    }

    public static int hash(String line) {
        int hash = 0;
        for(char c : line.toCharArray()) {

            hash += (int) c;
            hash *= 17;
            hash %= 256;

        }
        return hash;
    }

}
