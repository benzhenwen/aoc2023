package day_23;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Path {

    private static final char[][] MAP = Main.map;

    private final HashSet<Position> previousMoves;
    private Position currentPosition;
    private int length;

    public Path(Position p) {
        this(p, new HashSet<Position>(), 0);
    }

    public Path(Position p, HashSet<Position> previousMoves, int length) {
        this.currentPosition = p;
        this.previousMoves = previousMoves;
        this.length = length;
    }

    public Collection<Path> advance() {
        previousMoves.add(currentPosition.clone());
        ArrayList<Path> newPaths = new ArrayList<>(3);

        char currentChar = MAP[currentPosition.row][currentPosition.col];
        if(currentChar == '^') {
            this.currentPosition.move(Direction.UP);
            if(!previousMoves.contains(currentPosition)) {
                this.length++;
                newPaths.add(this);
            }
            return newPaths;
        }
        if(currentChar == 'v') {
            this.currentPosition.move(Direction.DOWN);
            if(!previousMoves.contains(currentPosition)) {
                this.length++;
                newPaths.add(this);
            }
            return newPaths;
        }
        if(currentChar == '<') {
            this.currentPosition.move(Direction.LEFT);
            if(!previousMoves.contains(currentPosition)) {
                this.length++;
                newPaths.add(this);
            }
            return newPaths;
        }
        if(currentChar == '>') {
            this.currentPosition.move(Direction.RIGHT);
            if(!previousMoves.contains(currentPosition)) {
                this.length++;
                newPaths.add(this);
            }
            return newPaths;
        }

        boolean memorySave = true;
        Position thisPosition = currentPosition.clone();
        int newLength = length+1;

        for(Direction d : Direction.values()) {
            Position newPosition = thisPosition.clone();
            newPosition.move(d);

            if(newPosition.inBounds(MAP) && newPosition.getValue(MAP) != '#' && !previousMoves.contains(newPosition)) {
                if(memorySave) {
                    memorySave = false;
                    this.currentPosition = newPosition;
                    this.length = newLength;
                    newPaths.add(this);
                }
                else {
                    newPaths.add(new Path(newPosition, (HashSet<Position>) previousMoves.clone(), newLength));
                }
            }
        }

        return newPaths;
    }

    public int getLength() {
        return length;
    }

    public Position getPosition() {
        return currentPosition;
    }

    public boolean effectivelySame(Path other) {
        if(this == other) return false;
        if(this.length != other.length) return false;
        if(!this.currentPosition.equals(other.currentPosition)) return false;
        else {
            for(Position p : previousMoves) {
                if(!other.previousMoves.contains(p)) return false;
            }
        }
        return true;
    }

}
