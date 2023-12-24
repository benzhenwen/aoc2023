package day_16;

import day_16.Beam.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Space {

    private HashMap<Direction, Direction[]> directionMap = new HashMap<>();

    public Space(char c) {
        switch(c) {
            case '|':
                directionMap.put(Direction.EAST, new Direction[]{Direction.NORTH, Direction.SOUTH});
                directionMap.put(Direction.WEST, new Direction[]{Direction.NORTH, Direction.SOUTH});
                break;
            case '-':
                directionMap.put(Direction.NORTH, new Direction[]{Direction.EAST, Direction.WEST});
                directionMap.put(Direction.SOUTH, new Direction[]{Direction.EAST, Direction.WEST});
                break;
            case '/':
                directionMap.put(Direction.NORTH, new Direction[]{Direction.EAST});
                directionMap.put(Direction.EAST, new Direction[]{Direction.NORTH});
                directionMap.put(Direction.SOUTH, new Direction[]{Direction.WEST});
                directionMap.put(Direction.WEST, new Direction[]{Direction.SOUTH});
                break;
            case '\\':
                directionMap.put(Direction.NORTH, new Direction[]{Direction.WEST});
                directionMap.put(Direction.WEST, new Direction[]{Direction.NORTH});
                directionMap.put(Direction.SOUTH, new Direction[]{Direction.EAST});
                directionMap.put(Direction.EAST, new Direction[]{Direction.SOUTH});
        }
    }

    private HashSet<Direction> seenBeforeDirections = new HashSet<>();

    public Beam[] reflectBeam(Beam b) {
        if(seenBeforeDirections.contains(b.direction)) return new Beam[]{};
        seenBeforeDirections.add(b.direction);

        if(!directionMap.containsKey(b.direction)) { // in the case that the space just does nothing, pass the beam
            b.move();
            return new Beam[]{b};
        }

        ArrayList<Beam> output = new ArrayList<>(2);
        for(Direction d : directionMap.get(b.direction)) {
            Beam newBeam = new Beam(d, b.position.clone());
            newBeam.move();
            output.add(newBeam);
        }

        return output.toArray(new Beam[0]);

    }

    public void reset() {
        seenBeforeDirections.clear();
    }

}
