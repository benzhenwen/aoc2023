package day_17;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_17_data.txt"));

        ArrayList<char[]> mapTemp = new ArrayList<>();
        while (s.hasNextLine()) {
            mapTemp.add(s.nextLine().toCharArray());
        }

        char[][] charMap = mapTemp.toArray(new char[0][]);

        Space[][] map = new Space[charMap.length][charMap[0].length];

        for(int i = 0; i < charMap.length; i++) {
            for(int j = 0; j < charMap[0].length; j++) {
                map[i][j] = new Space(Integer.parseInt("" + charMap[i][j]));
            }
        }

        for(int i = 0; i < charMap.length; i++) {
            for (int j = 0; j < charMap[0].length; j++) {
                map[i][j].initAdjacents(
                        getSpace(i-1, j, map),
                        getSpace(i+1, j, map),
                        getSpace(i, j-1, map),
                        getSpace(i, j+1, map)
                );
            }
        }

        Space startSpace = map[map.length-1][map[0].length-1];
        startSpace.makeStart();

        Queue<Space> queue = new LinkedList<>(startSpace.getPossibleLinks());

        try {
            while (!queue.isEmpty()) {
                Space space = queue.remove();
                if(space == map[0][0]) {
                    System.out.println("ngklsdjglksd");
                }
                Collection<Space> c = space.link();
                if (space != startSpace) { // the initial space cannot be re-linked
                    for(Space addSpace : c) if(!queue.contains(addSpace) && map[map.length-1][map[0].length-1] != addSpace) {
                        queue.add(addSpace);
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

    public static Space getSpace(int row, int col, Space[][] map) {
        if(row < 0 || row >= map.length || col < 0 || col >= map[0].length) return null;
        return map[row][col];
    }

    public static void printFind(Space s, Space[][] map) {
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

    public static String directionToString(Space.Direction d) {
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
