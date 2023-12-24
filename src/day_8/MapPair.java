package day_8;

public class MapPair {

    public String left;
    public String right;

    public MapPair(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String get(boolean direction) {
        if(direction) return right;
        return left;
    }

    public String toString() {
        return left + " " + right;
    }

}
