package day_19;

import java.util.ArrayList;
import java.util.Collection;

public class DynamicParty {

    public String location;
    public TraitRange x, m, a, s;

    public DynamicParty(String location, TraitRange x, TraitRange m, TraitRange a, TraitRange s) {
        this.location = location;
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
    }

    public Collection<DynamicParty> split(Workflow workflow) {
        ArrayList<DynamicParty> output = new ArrayList<>(4);

        ArrayList<TraitRange> xRanges = new ArrayList<>(2);
        ArrayList<TraitRange> mRanges = new ArrayList<>(2);
        ArrayList<TraitRange> aRanges = new ArrayList<>(2);
        ArrayList<TraitRange> sRanges = new ArrayList<>(2);

        xRanges.add(this.x);
        mRanges.add(this.m);
        aRanges.add(this.a);
        sRanges.add(this.s);

        for(int i = 0; i < workflow.conditions.length; i++) {
            ArrayList<TraitRange> toSplit = null;
            switch(workflow.conditions[i].trait) {
                case 'x' -> toSplit = xRanges;
                case 'm' -> toSplit = mRanges;
                case 'a' -> toSplit = aRanges;
                case 's' -> toSplit = sRanges;
            }
            ArrayList<TraitRange> newRanges = new ArrayList<>(toSplit.size()*2);
            for(TraitRange t : toSplit) {
                newRanges.addAll(t.split(workflow.conditions[i]));
            }
            switch(workflow.conditions[i].trait) {
                case 'x' -> xRanges = newRanges;
                case 'm' -> mRanges = newRanges;
                case 'a' -> aRanges = newRanges;
                case 's' -> sRanges = newRanges;
            }
        }

        // add every combination of the new ranges
        for (TraitRange xRange : xRanges) {
            for (TraitRange mRange : mRanges) {
                for (TraitRange aRange : aRanges) {
                    for (TraitRange sRange : sRanges) {
                        output.add(new DynamicParty(workflow.run(new Party(xRange.min, mRange.min, aRange.min, sRange.min)), xRange, mRange, aRange, sRange));
                    }
                }
            }
        }

        return output;
    }

    public long getAmountRepresented() {
        return x.getRange() * m.getRange() * a.getRange() * s.getRange();
    }

}
