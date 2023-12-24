package day_17;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Space_2 {

    public enum Direction {UP, DOWN, LEFT, RIGHT};

    public HashMap<Direction, Space_2> adjacentSpaces = new HashMap<>();
    public HashMap<Direction, Integer> pathCosts = new HashMap<>();
    public HashMap<Direction, Integer> chainLengths = new HashMap<>();
    public final int cost;

    public boolean runOnce = false;

    public Space_2(int cost) {
        this.cost = cost;

        pathCosts.put(Direction.UP, Integer.MAX_VALUE);
        pathCosts.put(Direction.DOWN, Integer.MAX_VALUE);
        pathCosts.put(Direction.LEFT, Integer.MAX_VALUE);
        pathCosts.put(Direction.RIGHT, Integer.MAX_VALUE);
        chainLengths.put(Direction.UP, 100);
        chainLengths.put(Direction.DOWN, 100);
        chainLengths.put(Direction.LEFT, 100);
        chainLengths.put(Direction.RIGHT, 100);
    }

    public void initAdjacents(Space_2 up, Space_2 down, Space_2 left, Space_2 right) {
        adjacentSpaces.put(Direction.UP, up);
        adjacentSpaces.put(Direction.DOWN, down);
        adjacentSpaces.put(Direction.LEFT, left);
        adjacentSpaces.put(Direction.RIGHT, right);
    }

    public void makeStart() {
        pathCosts.put(Direction.UP, cost);
        pathCosts.put(Direction.DOWN, cost);
        pathCosts.put(Direction.LEFT, cost);
        pathCosts.put(Direction.RIGHT, cost);
        chainLengths.put(Direction.UP, 1);
        chainLengths.put(Direction.DOWN, 1);
        chainLengths.put(Direction.LEFT, 1);
        chainLengths.put(Direction.RIGHT, 1);
    }

    public Collection<Space_2> getAdjacentSpaces() {
        return adjacentSpaces.values();
    }

    public Collection<Space_2> getPossibleLinks() {
        ArrayList<Space_2> output = new ArrayList<>(24);
        for(Direction d : Direction.values()) {
            Space_2 spaceToAdd = this;
            for(int i = 1; i <= 10; i++) {
                spaceToAdd = spaceToAdd.getSpace(d);
                if(spaceToAdd == null) {
                    break;
                }
                if(i >= 3) {
                    output.add(spaceToAdd);
                }
            }
        }
        return output;
    }

    public Collection<Space_2> getPossibleLinksInDirection(Direction d) {
        ArrayList<Space_2> output = new ArrayList<>(6);
        Space_2 spaceToAdd = this;
        for(int i = 1; i <= 10; i++) {
            spaceToAdd = spaceToAdd.getSpace(d);
            if(spaceToAdd == null) {
                break;
            }
            if(i >= 3) {
                output.add(spaceToAdd);
            }
        }
        return output;
    }

    public Collection<Space_2> link() {

        ArrayList<Space_2> reevaluate = new ArrayList<>(24);

        // calculate the cost from every input direction
        for(Direction inputDirection : Direction.values()) {

            // log the best direction to go in from the input direction
            chainLengthReturn = 100;
            int bestOutputCost = getCost(inputDirection, 1);

            // log the best cost in the corresponding direction assuming a path was found
            if(bestOutputCost+cost < pathCosts.get(inputDirection) && bestOutputCost != Integer.MAX_VALUE) {
                pathCosts.put(inputDirection, bestOutputCost + cost);
                chainLengths.put(inputDirection, chainLengthReturn);

                // re-check stuff
                reevaluate.addAll(getPossibleLinks());
            }

        }

        return reevaluate;

    }

    private static int chainLengthReturn;

    public int getCost(Direction d, int chainLength) {

        chainLengthReturn = chainLength;

        // triple chain, stop here (end of recursive algorithm)
        if(chainLength > 10) {
            return Integer.MAX_VALUE;
        }

        int output = Integer.MAX_VALUE;

        if(chainLength <= 3) { // must go forward

            if(getSpace(d) != null) {
                output = getSpace(d).getCost(d, chainLength + 1);
                if (output != Integer.MAX_VALUE && chainLength > 1) output += cost;
            }

        }

        else {
            for (Direction outputDirection : getOtherDirections(reverseDirection(d))) { // directions going "forward, left, right"

                Space_2 outputDirectionSpace = getSpace(outputDirection);

                if (outputDirectionSpace != null) {
                    int directionCost = Integer.MAX_VALUE;
                    if (outputDirection == d) {
                        directionCost = outputDirectionSpace.getCost(outputDirection, chainLength + 1);
                    }
                    else {
                        if(outputDirectionSpace.chainLengths.get(outputDirection) > 3) {
                            directionCost = outputDirectionSpace.pathCosts.get(outputDirection);
                        }
                    }

                    if (directionCost != Integer.MAX_VALUE) directionCost += cost;
                    if (directionCost < 0) directionCost = Integer.MAX_VALUE;

                    if (directionCost < output) output = directionCost;
                }
            }
        }

        if(output == Integer.MAX_VALUE && chainLength > 3 &&
                 chainLengths.get(d) + chainLength <= 11
        ) {
            return pathCosts.get(d);
        }

        return output;

    }

    public Space_2 getSpace(Direction d) {
        return adjacentSpaces.get(d);
    }

    public Direction directionOf(Space_2 s) {
        if(getSpace(Direction.UP) == s) return Direction.UP;
        if(getSpace(Direction.DOWN) == s) return Direction.DOWN;
        if(getSpace(Direction.LEFT) == s) return Direction.LEFT;
        if(getSpace(Direction.RIGHT) == s) return Direction.RIGHT;
        throw new RuntimeException("space not adjacent");
    }

    public Direction getDirection() {
        return null;
    }

    public Collection<Direction> getOtherDirections(Direction d) {
        ArrayList<Direction> output = new ArrayList<>(3);
        for(Direction d2 : Direction.values()) {
            if(d2 != d) output.add(d2);
        }
        return output;
    }

    public Direction reverseDirection(Direction d) {
        if(d == Direction.UP) return Direction.DOWN;
        if(d == Direction.DOWN) return Direction.UP;
        if(d == Direction.LEFT) return Direction.RIGHT;
        if(d == Direction.RIGHT) return Direction.LEFT;
        throw new RuntimeException("invalid direction");
    }


    public int theoreticalLowest() {
        int output = Integer.MAX_VALUE;
        for(Direction d : Direction.values()) {
            if(pathCosts.containsKey(d)) {
                if (pathCosts.get(d) < output) output = pathCosts.get(d);
            }
        }
        if(output == Integer.MAX_VALUE) return -1;
        return output;
    }

    public String toString() {
        return "" + theoreticalLowest();
    }

}
