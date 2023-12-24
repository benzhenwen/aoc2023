package day_12;

import java.io.File;
import java.util.*;

public class Main_2 {

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

        int output = 0;

        for(int l = 0; l < map.length; l++) {

            char[] line = map[l];
            int[] sepVals = seperations[l];

            Queue<Integer> uPoints = new LinkedList<>();
            for(int i = 0; i < line.length; i++) {
                if(line[i] == '?') uPoints.add(i);
            }

            boolean[] fakeMap = new boolean[line.length];

            output += search(line, fakeMap, 0, sepVals);

            System.out.println("line " + l + " finished");

        }

        System.out.println(output);

    }

    public static int search(char[] map, boolean[] fakeMap, int index, int[] key) {
        if(index == map.length) {
            if(spacingStructureEquals(fakeMap, key, index)) return 1;
            return 0;
        }
        int sum = 0;
        if(index != 0 && map[index] == '.' && map[index-1] != '.') { // found the first dot in after a sequence on non-dots
            if(spacingStructureEquals(fakeMap, key, index)) { // everything is valid up to index
                sum += search(map, fakeMap, index+1, key);
            }
        }
        else if(map[index] == '?') {
                boolean[] mapTrue = fakeMap.clone();
                boolean[] mapFalse = fakeMap.clone();
                mapTrue[index] = true;
                mapFalse[index] = false;
                if(spacingStructureEquals(mapTrue, key, index)) {
                    sum += search(map, mapTrue, index + 1, key);
                }
                if(spacingStructureEquals(mapFalse, key, index)) {
                    sum += search(map, mapFalse, index + 1, key);
                }
        }
        else if(map[index] == '#') {
            boolean[] newMap = fakeMap.clone();
            newMap[index] = true;
            sum += search(map, newMap, index+1, key);
        }
        else {
            sum += search(map, fakeMap, index+1, key);
        }
        return sum;
    }

    public static boolean spacingStructureEquals(boolean[] input, int[] structure, int depth) {
        ArrayList<Integer> output = new ArrayList<>();

        int searchIndex = 0;

        int currentSum = 0;
        for(int i = 0; i < depth; i++) {
            if(input[i]) {
                if(searchIndex >= structure.length) return false;
                currentSum++;
            }
            else if(currentSum > 0) {
                if(currentSum != structure[searchIndex++]) return false;
                currentSum = 0;
            }
        }

        if(currentSum > 0 && currentSum >= structure[searchIndex] && currentSum != structure[searchIndex++]) return false;

        return depth != input.length || searchIndex == structure.length;

    }

}
