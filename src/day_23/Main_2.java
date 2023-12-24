package day_23;

import java.io.File;
import java.util.*;

public class Main_2 {

    public static char[][] map;
    public static HashMap<Position, Intersection> intersections;

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_23_data.txt"));

        ArrayList<char[]> mapTemp = new ArrayList<>();
        while (s.hasNextLine()) {
            mapTemp.add(s.nextLine().toCharArray());
        }

        map = mapTemp.toArray(new char[0][]);

        // identify intersections
        intersections = new HashMap<>();

        for(int row = 1; row < map.length-1; row++) {
            for(int col = 1; col < map[0].length-1; col++) {
                int totalOpenings = 0;
                if(map[row+1][col] != '#') totalOpenings++;
                if(map[row-1][col] != '#') totalOpenings++;
                if(map[row][col+1] != '#') totalOpenings++;
                if(map[row][col-1] != '#') totalOpenings++;
                if(totalOpenings >= 3) intersections.put(new Position(row, col), new Intersection(new Position(row, col)));
            }
        }

        Position startPos = new Position(0, 1);
        Position endPos = new Position(map.length-1, map[0].length-2);
        Intersection start = new Intersection(startPos);
        Intersection end = new Intersection(endPos);
        intersections.put(startPos, end);
        intersections.put(endPos, end);

        // link together the intersection
        Queue<Intersection> queue = new LinkedList<>();
        queue.add(start);

        while(!queue.isEmpty()) {
            Intersection i = queue.remove();
            for(Direction d : Direction.values()) {
                Position positionToCheck = i.getPosition().clone();
                positionToCheck.move(d);
                if(positionToCheck.inBounds(map) && positionToCheck.getValue(map) != '#') {
                    Intersection toLink = findConnectedIntersection(positionToCheck, i.getPosition());
                    i.linkTo(toLink, distance);
                    if(!toLink.alreadyLinkedTo(i)) {
                        queue.add(toLink);
                    }
                }
            }
        }

        System.out.println(start.longestDistanceTo(end));

    }

    private static int distance;
    private static Position previousPosition;
    public static Intersection findConnectedIntersection(Position p, Position pp) {
        previousPosition = pp;
        distance = 1;

        while(!intersections.containsKey(p)) {
            distance++;
            Direction nextDirection = Direction.UP;
            p.move(Direction.DOWN);
            if(p.getValue(map) != '#' && !p.equals(previousPosition)) nextDirection = Direction.DOWN;
            p.move(Direction.UP);
            p.move(Direction.LEFT);
            if(p.getValue(map) != '#' && !p.equals(previousPosition)) nextDirection = Direction.LEFT;
            p.move(Direction.RIGHT);
            p.move(Direction.RIGHT);
            if(p.getValue(map) != '#' && !p.equals(previousPosition)) nextDirection = Direction.RIGHT;
            p.move(Direction.LEFT);

            previousPosition = p.clone();
            p.move(nextDirection);
        }

        return intersections.get(p);
    }

}
