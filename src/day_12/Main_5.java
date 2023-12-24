package day_12;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;



public class Main_5 {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(new File("data/puzzle_12_data.txt"));

        int rows = 1000;

        char[][] map = new char[rows][];
        int[][] seperations = new int[rows][];

        int index = 0;
        while(sc.hasNextLine()) {

            String line = sc.nextLine();
            map[index] = line.split(" ")[0].toCharArray();

            String[] numbers = line.split(" ")[1].split(",");

            seperations[index] = new int[numbers.length];
            for(int i = 0; i < numbers.length; i++) {
                seperations[index][i] = Integer.parseInt(numbers[i]);
            }

            index++;
        }

        for(int l = 0; l < map.length; l++) {
            char[] m = map[l];
            char[] newM = new char[m.length*5 + 4];
            for(int i = 0; i < newM.length; i++) {
                int i2 = i % (m.length+1);
                if(i2 == m.length) newM[i] = '?';
                else newM[i] = m[i2];
            }
            map[l] = newM;

            int[] s = seperations[l];
            int[] newS = new int[s.length*5];
            for(int i = 0; i < newS.length; i++) {
                newS[i] = s[i%s.length];
            }
            seperations[l] = newS;
        }

        long output = 0;

        long timeStart = System.nanoTime();

        for(int l = 0; l < map.length; l++) {

            char[] line = map[l];
            int[] sepVals = seperations[l];

            output += combos(line, sepVals);

            System.out.println("line " + l + " finished");

        }

        long endTime = System.nanoTime();

        System.out.println("output: " + output);
        System.out.println((endTime - timeStart) / 1000000.0 + "ms");

    }

    public static long combos(char[] map, int[] key) {

        // update map
        char[] newMap = new char[map.length+2];
        newMap[0] = '.';
        newMap[newMap.length-1] = '.';
        for(int i = 1; i < newMap.length-1; i++) {
            newMap[i] = map[i-1];
        }

        // create new map
        int len = key.length+1;
        for(int i : key) len += i;
        boolean[] newKey = new boolean[len];
        int index = 1;
        for(int i : key) {
            for(int j = 0; j < i; j++) {
                newKey[index++] = true;
            }
            index++;
        }

        int n = newMap.length;
        int m = newKey.length;
        long[][] dp = new long[n+1][m+1];
        dp[n][m] = 1;

        for(int i = n-1; i >= 0; i--) {
            for(int j = m-1; j >= 0; j--) {
                boolean d = true, o = true;
                if(newMap[i] == '#') {
                    o = false;
                }
                else if(newMap[i] == '.') {
                    d = false;
                }

                long sum = 0;
                if(d && newKey[j]) {
                    dp[i][j] = dp[i+1][j+1];
                }
                else if(o && !newKey[j]) {
                    dp[i][j] = dp[i+1][j+1] + dp[i+1][j];
                }
            }
        }
        return dp[0][0];
    }

}
