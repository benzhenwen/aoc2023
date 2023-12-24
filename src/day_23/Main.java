package day_23;

import day_22.Block;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static char[][] map;

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_23_data.txt"));

        ArrayList<char[]> mapTemp = new ArrayList<>();
        while (s.hasNextLine()) {
            mapTemp.add(s.nextLine().toCharArray());
        }

        map = mapTemp.toArray(new char[0][]);

        int longestPath = 0;
        Position end = new Position(map.length-1, map[0].length-2);

        Queue<Path> paths = new LinkedList<>();
        paths.add(new Path(new Position(0, 1)));

        while(!paths.isEmpty()) {
            Path p = paths.remove();
            if(p.getPosition().equals(end)) {
                if(p.getLength() > longestPath) longestPath = p.getLength();
            }
            else paths.addAll(p.advance());
        }

        System.out.println(longestPath);

    }

}
