package day_17;

import java.util.*;

public class Space {

    public enum Direction {UP, DOWN, LEFT, RIGHT};

    public HashMap<Direction, Space> adjacentSpaces = new HashMap<>();
    public HashMap<Direction, Integer> pathCosts = new HashMap<>();
    public final int cost;
    public HashMap<Direction, Boolean> initializedFrom = new HashMap<>();

    public Space(int cost) {
        this.cost = cost;
    }

    public void initAdjacents(Space up, Space down, Space left, Space right) {
        adjacentSpaces.put(Direction.UP, up);
        adjacentSpaces.put(Direction.DOWN, down);
        adjacentSpaces.put(Direction.LEFT, left);
        adjacentSpaces.put(Direction.RIGHT, right);
        initializedFrom.put(Direction.UP, false);
        initializedFrom.put(Direction.DOWN, false);
        initializedFrom.put(Direction.LEFT, false);
        initializedFrom.put(Direction.RIGHT, false);
    }

    public void makeStart() {
        pathCosts.put(Direction.UP, cost);
        pathCosts.put(Direction.DOWN, cost);
        pathCosts.put(Direction.LEFT, cost);
        pathCosts.put(Direction.RIGHT, cost);
        initializedFrom.put(Direction.UP, true);
        initializedFrom.put(Direction.DOWN, true);
        initializedFrom.put(Direction.LEFT, true);
        initializedFrom.put(Direction.RIGHT, true);
    }

    public Collection<Space> getAdjacentSpaces() {
        return adjacentSpaces.values();
    }

    public Collection<Space> getPossibleLinks() {
        ArrayList<Space> output = new ArrayList<>(4);
        for(Space s : getAdjacentSpaces()) {
            if(s != null) output.add(s);
        }
        return output;
    }

    public Collection<Space> link() {

        ArrayList<Space> reevaluate = new ArrayList<>(3);

        // calculate the cost from every input direction
        for(Direction inputDirection : Direction.values()) {

            // log the best direction to go in from the input direction
            int bestOutputCost = getCost(inputDirection, 1);

            // log the best cost in the corresponding direction assuming a path was found
            if(bestOutputCost != Integer.MAX_VALUE && (!initializedFrom.get(inputDirection) || bestOutputCost+cost < pathCosts.get(inputDirection))) {
                pathCosts.put(inputDirection, bestOutputCost + cost);
                initializedFrom.put(inputDirection, true);

                // the input in this direction was changed, so we should make sure to re-check that space
                Space reevalSpace = getSpace(reverseDirection(inputDirection));
                if(reevalSpace != null) reevaluate.add(reevalSpace);
            }

        }

        return reevaluate;

    }

    public int getCost(Direction d, int chainLength) {

        // triple chain, stop here (end of recursive algorithm)
        if(chainLength > 3) return Integer.MAX_VALUE;

        int output = Integer.MAX_VALUE;
        for(Direction outputDirection : getOtherDirections(reverseDirection(d))) { // directions going "forward, left, right"

            Space outputDirectionSpace = getSpace(outputDirection);

            if(outputDirectionSpace != null && outputDirectionSpace.initializedFrom.get(outputDirection)) {
                int directionCost;
                if (outputDirection == d) {
                    directionCost = outputDirectionSpace.getCost(outputDirection, chainLength + 1);
                }
                else directionCost = outputDirectionSpace.pathCosts.get(outputDirection);

                if(chainLength > 1 && directionCost != Integer.MAX_VALUE) directionCost += cost;

                if(directionCost < output) output = directionCost;
            }

        }

        if(output == Integer.MAX_VALUE && initializedFrom.get(d)) {
            return pathCosts.get(d);
        }

        return output;

    }

    public Space getSpace(Direction d) {
        return adjacentSpaces.get(d);
    }

    public Direction directionOf(Space s) {
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
