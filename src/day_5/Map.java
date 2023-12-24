package day_5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class Map {

    HashSet<Range> Ranges = new HashSet<>();

    public Long getOutput(Long input) {
        for(Range r : Ranges) {
            if(r.inRange(input)) {
                return r.convert(input);
            }
        }
        return input;
    }

    public Iterable<MinMax> getCriticalRanges(Iterable<MinMax> input) {
        HashSet<MinMax> criticalRanges = new HashSet<>();

        System.out.println("mm input: " + input);

        for(MinMax mm : input) {

            for (Range r : Ranges) {
                Long sourceStart = r.sourceStart;
                Long sourceEnd = r.sourceStart + r.length;
                ArrayList<Long> critPoints = new ArrayList<>();
                critPoints.add(mm.min);
                critPoints.add(mm.max);
                if (mm.containsInside(sourceStart)) critPoints.add(sourceStart);
                if (mm.containsInside(sourceEnd)) critPoints.add(sourceEnd);
                critPoints.sort(Comparator.naturalOrder());

                for (int i = 0; i < critPoints.size() - 1; i++) {
                    criticalRanges.add(new MinMax(critPoints.get(i), critPoints.get(i + 1)));
                }
            }
        }

        MinMax[] checkArray = criticalRanges.toArray(new MinMax[0]);
        for(int i = 0; i < criticalRanges.size(); i++) {
            MinMax check = checkArray[i];
            criticalRanges.removeIf(mm -> check.contains(mm) && mm != check);
        }

        System.out.println("mm divide: " + criticalRanges);

        for(MinMax mm : criticalRanges) {
            mm.min = getOutput(mm.min);
            mm.max = getOutput(mm.max-1)+1;
        }

        System.out.println("mm output: " + criticalRanges);

        return criticalRanges;
    }



}
