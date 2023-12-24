package day_18;

public class VerticalLine implements Comparable<VerticalLine>{

    public long col;
    public long start, end;

    public VerticalLine(long col, long start, long end) {
        this.col = col;
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    public boolean inRange(long row) {
        return row >= start && row <= end;
    }

    public boolean containRange(long row) {
        return row > start && row < end;
    }

    @Override
    public int compareTo(VerticalLine other) {
         if(this.col > other.col) return 1;
         if(this.col == other.col) return 0;
         return -1;
    }

    public String toString() {
        return col + " | " + start + " -> " + end;
    }

}
