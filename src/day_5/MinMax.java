package day_5;

import java.util.Objects;

public class MinMax {

    public Long min; // inclusive
    public Long max; // exclusive

    public MinMax(Long min, Long max) {
        this.min = min;
        this.max = max;
    }

    public boolean contains(Long input) {
        return input >= min && input < max;
    }

    public boolean containsInside(Long input) {
        return input > min && input < max;
    }

    public boolean contains(MinMax input) {
        return min >= input.min && max <= input.max;
    }

    public String toString() {
        return min + "-" + (max);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinMax minMax = (MinMax) o;
        return Objects.equals(min, minMax.min) && Objects.equals(max, minMax.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
