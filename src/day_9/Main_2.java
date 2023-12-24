package day_9;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;

public class Main_2 {

    public static void main(String[] args) throws Exception{

        Scanner s = new Scanner(new File("data/puzzle_9_data.txt"));

        int sum = 0;

        while(s.hasNext()) {

            Stack<int[]> layers = new Stack<>();
            layers.add(parseLine(s.nextLine()));

            while(!isZero(layers.peek())) {

                int[] pastLayer = layers.peek();
                int[] newLayer = new int[pastLayer.length-1];
                for(int i = 0; i < newLayer.length; i++) {
                    newLayer[i] = pastLayer[i+1] - pastLayer[i];
                }
                layers.push(newLayer);

            }

            layers.pop();
            int nextValue = 0;

            while(layers.size() > 0) {
                int[] poppedLayer = layers.pop();
                nextValue = poppedLayer[0] - nextValue;
            }

            sum += nextValue;

        }

        System.out.println(sum);

    }

    private static boolean isZero(int[] input) {
        for(int i : input) if(i != 0) return false;
        return true;
    }

    private static int[] parseLine(String input) {
        String[] spots = input.split(" ");
        int[] output = new int[spots.length];

        for(int i = 0; i < spots.length; i++) {
            output[i] = Integer.parseInt(spots[i]);
        }

        return output;
    }

}
