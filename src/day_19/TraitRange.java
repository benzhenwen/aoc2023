package day_19;

import java.util.ArrayList;
import java.util.Collection;

public class TraitRange {

    int min, max;

    public TraitRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public Collection<TraitRange> split(Condition c) {
        ArrayList<TraitRange> output = new ArrayList<>(2);
        if(c.value < min || c.value > max) {
            output.add(this);
            return output;
        }
        int splitPoint = c.value;
        if(c.condition) splitPoint++;
        output.add(new TraitRange(min, splitPoint-1));
        output.add(new TraitRange(splitPoint, max));
        return output;
    }

    public long getRange() {
        return max - min + 1;
    }

}
