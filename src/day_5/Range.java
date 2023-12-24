package day_5;

public class Range {

    public Long desinationStart, sourceStart, length;

    public Range(Long d, Long s, Long l) {
        desinationStart = d;
        sourceStart = s;
        length = l;
    }

    public boolean inRange(Long input) {
        return input >= sourceStart && input < sourceStart + length;
    }

    public Long convert(Long input) {
        if(inRange(input)) return input - sourceStart + desinationStart;
        return input;
    }

}
