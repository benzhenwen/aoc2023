package day_16;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_16_data.txt"));

        ArrayList<char[]> mapTemp = new ArrayList<>();
        while (s.hasNextLine()) {
            mapTemp.add(s.nextLine().toCharArray());
        }

        char[][] charMap = mapTemp.toArray(new char[0][]);

        Space[][] map = new Space[charMap.length][charMap[0].length];

        for(int i = 0; i < charMap.length; i++) {
            for(int j = 0; j < charMap[0].length; j++) {
                map[i][j] = new Space(charMap[i][j]);
            }
        }

        int maxEnergy = -1;

        for(int i = 0; i < map[0].length; i++) {
            int energy = getEnergized(new Beam(Beam.Direction.SOUTH, new Position(0, i)), map);
            if(energy > maxEnergy) maxEnergy = energy;
            clear(map);
        }

        for(int i = 0; i < map[0].length; i++) {
            int energy = getEnergized(new Beam(Beam.Direction.NORTH, new Position(map.length-1, i)), map);
            if(energy > maxEnergy) maxEnergy = energy;
            clear(map);
        }

        for(int i = 0; i < map.length; i++) {
            int energy = getEnergized(new Beam(Beam.Direction.EAST, new Position(i, 0)), map);
            if(energy > maxEnergy) maxEnergy = energy;
            clear(map);
        }

        for(int i = 0; i < map.length; i++) {
            int energy = getEnergized(new Beam(Beam.Direction.WEST, new Position(i, map[0].length-1)), map);
            if(energy > maxEnergy) maxEnergy = energy;
        }

        System.out.println("output: " + maxEnergy);

    }

    public static int getEnergized(Beam startingBeam, Space[][] map) {
        // computation
        boolean[][] traversedMap = new boolean[map.length][map[0].length];

        Queue<Beam> toProcess = new LinkedList<>();
        toProcess.add(startingBeam);

        while(!toProcess.isEmpty()) {
            Beam b = toProcess.remove();
            traversedMap[b.position.row][b.position.col] = true;

            for(Beam newBeam : map[b.position.row][b.position.col].reflectBeam(b)) {
                if (newBeam.position.inBounds(map)) toProcess.add(newBeam);
            }

        }

        int output = 0;
        for(int i = 0; i < traversedMap.length; i++) {
            for(int j = 0; j < traversedMap[0].length; j++) {
                if(traversedMap[i][j]) output++;
            }
        }

        return output;
    }

    public static void clear(Space[][] map) {
        for(Space[] l : map) {
            for(Space p : l) p.reset();
        }
    }

}
