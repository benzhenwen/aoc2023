package day_16;

import java.io.File;
import java.util.*;

public class Main {

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

        // computation
        boolean[][] traversedMap = new boolean[map.length][map[0].length];

        Queue<Beam> toProcess = new LinkedList<>();
        toProcess.add(new Beam(Beam.Direction.EAST, new Position(0, 0)));

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


        System.out.println(output);

    }

}
