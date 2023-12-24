package day_17;

import java.io.File;
import java.util.*;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_17_data.txt"));

        ArrayList<char[]> mapTemp = new ArrayList<>();
        while (s.hasNextLine()) {
            mapTemp.add(s.nextLine().toCharArray());
        }

        char[][] charMap = mapTemp.toArray(new char[0][]);

        Space_2[][] map = new Space_2[charMap.length][charMap[0].length];

        for(int i = 0; i < charMap.length; i++) {
            for(int j = 0; j < charMap[0].length; j++) {
                map[i][j] = new Space_2(Integer.parseInt("" + charMap[i][j]));
            }
        }

        for(int i = 0; i < charMap.length; i++) {
            for (int j = 0; j < charMap[0].length; j++) {
                map[i][j].initAdjacents(
                        getSpace_2(i-1, j, map),
                        getSpace_2(i+1, j, map),
                        getSpace_2(i, j-1, map),
                        getSpace_2(i, j+1, map)
                );
            }
        }

        Space_2 startSpace_2 = map[map.length-1][map[0].length-1];
        startSpace_2.makeStart();

        Queue<Space_2> queue = new LinkedList<>(startSpace_2.getPossibleLinks());

        int oldMin = Integer.MAX_VALUE;

        try {
            while (!queue.isEmpty()) {
                Space_2 space = queue.remove();
                if(find(space, map) < oldMin) {
                    oldMin = find(space, map);
                    printFind(space, map);
                    System.out.println();
                }
                Collection<Space_2> c = space.link();
                if (space != startSpace_2) { // the initial space cannot be re-linked
                    for(Space_2 addSpace_2 : c) {
                        if(!queue.contains(addSpace_2) && map[map.length-1][map[0].length-1] != addSpace_2) {
                            queue.add(addSpace_2);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[0].length; j++) {
                    System.out.print(map[i][j].theoreticalLowest() + " ");
                }
                System.out.println();
            }
        }

        System.out.println(map[0][0].theoreticalLowest() - map[0][0].cost);
    }

    public static Space_2 getSpace_2(int row, int col, Space_2[][] map) {
        if(row < 0 || row >= map.length || col < 0 || col >= map[0].length) return null;
        return map[row][col];
    }

    public static void printFind(Space_2 s, Space_2[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] == s) {
                    System.out.print(j + " " + i);
                    return;
                }
            }
        }
        System.out.print("not found");
    }

    public static int find(Space_2 s, Space_2[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] == s) {
                    return i + j;
                }
            }
        }
        throw new RuntimeException("gdsg");
    }

    public static String directionToString(Space_2.Direction d) {
        String o = "";
        if(d == null) o = ".";
        else {
            switch (d) {
                case UP -> o += "^";
                case DOWN -> o += "v";
                case LEFT -> o += "<";
                case RIGHT -> o += ">";
            }
        }
        return o;
    }

}
