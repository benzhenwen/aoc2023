package day_23;

import java.util.*;

public class Intersection {
    private HashMap<Intersection, Integer> links;
    private Position position;

    public Intersection(Position p) {
        this.position = p;
        this.links = new HashMap<>();
    }

    public void linkTo(Intersection other, int distance) {
        if(links.containsKey(other) && links.get(other) > distance) return;
        links.put(other, distance);
    }

    public boolean alreadyLinkedTo(Intersection other) {
        return links.containsKey(other);
    }

    public int distanceTo(Intersection other) {
        return links.get(other);
    }

    public Position getPosition() {
        return position;
    }

    public int longestDistanceTo(Intersection other) {
        return longestDistanceTo(other, new HashSet<>());
    }

    private int longestDistanceTo(Intersection other, HashSet<Position> alreadyVisited) {
        if(other == this) return 0;
        alreadyVisited.add(position.clone());
        int pathDistance = Integer.MIN_VALUE;
        for(Map.Entry<Intersection, Integer> e : links.entrySet()) {
            if(!alreadyVisited.contains(e.getKey().position)) {
                int newPathDistance = e.getKey().longestDistanceTo(other, (HashSet<Position>) alreadyVisited.clone()) + e.getValue();
                if (newPathDistance > pathDistance) pathDistance = newPathDistance;
            }
        }
        return pathDistance;
    }

}
