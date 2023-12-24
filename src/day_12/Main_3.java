package day_12;

import java.io.File;
import java.util.Scanner;

public class Main_3 {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(new File("data/puzzle_12_data.txt"));

        int rows = 1;

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

            output += search(line, 0, sepVals, 0);

            System.out.println("line " + l + " finished");

        }

        long endTime = System.nanoTime();

        System.out.println(output);
        System.out.println((endTime - timeStart) / 1000000.0 + "ms");

    }

    // the goal of this algorithm is to find the earliest valid placement for the next length so that all proceeding values do not immediately violate the key.
    // the algorithm also has to accommodate for the already marked values. is a sequence starts with ##. and then is looking for 2 that key needs to be skipped.
    public static int search(char[] map, int index, int[] key, int keyIndex) {
        if(keyIndex >= key.length) return 1; // every key has been place successfully! add 1 to the count
        if(index + key[keyIndex] + getMinimumFollowingLength(key, keyIndex) > map.length) { // we've reached the end...
            return 0;
        }
        /*if(!hasImpact(map, index,key[keyIndex]+1)) { // there are more forced '#' here than the key allows so stop searching beyond this point
            return 0;
        }*/
        int sum = 0;
        if(hasSpace(map, index, key[keyIndex])) { // # key[keyIndex] length of trues can be placed in a sequence starting here
            if((index + key[keyIndex] >= map.length || map[index + key[keyIndex]] != '#')
                    && (index == 0 || map[index - 1] != '#')
            ) { // the immediately before or following value cannot be '#' or the sequence would be too long!
                sum += search(map, index + key[keyIndex] + 1, key, keyIndex+1); // step forward to go to the end of the sequence +1 to not interfere with the sequence in any way
            }
        }
        if(hasImpact(map, index, key[keyIndex])) { // in the case where every space was '#' this sum would double count line 83
            int nextIndex = index;
            do {if(++nextIndex >= map.length) break;} while(map[nextIndex] == '.'); // increment the index up to the next logical spot
            return sum + search(map, nextIndex, key, keyIndex); // search the next index. keyIndex is not incremented because no key locations were found/placed;
        }
        return sum;
    }

    public static boolean hasSpace(char[] map, int index, int length) {
        for(int i = index; i < index+length; i++) {
            if(map[i] == '.') return false; // can't place on a dot. theoretically can place on ? and #
        }
        return true;
    }

    public static boolean hasImpact(char[] map, int index, int length) {
        for(int i = index; i < index+length; i++) {
            if(map[i] != '#') return true; // can't place on a dot. theoretically can place on ? and #
        }
        return false;
    }

    public static int getMinimumFollowingLength(int[] key, int keyIndex) {
        int output = key.length-keyIndex-1;
        for(int i = keyIndex+1; i < key.length; i++) {
            output += key[i];
        }
        return output;
    }

}
