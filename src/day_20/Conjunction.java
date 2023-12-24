package day_20;

import java.util.Collection;
import java.util.HashMap;

public class Conjunction extends Module {

    public Conjunction(String name, String[] destinations) {super(name, destinations);}

    private final HashMap<String, Pulse> pulseInputMap = new HashMap<>();

    public PulseEvent[] run(String from, Pulse p) {
        pulseInputMap.put(from, p);

        Pulse outputPulse = allHigh() ? Pulse.LOW : Pulse.HIGH;
        PulseEvent[] output = new PulseEvent[destinations.length];
        for(int i = 0; i < destinations.length; i++) {
            output[i] = new PulseEvent(this.name, destinations[i], outputPulse);
        }

        return output;
    }

    private boolean allHigh() {
        for(Pulse p : pulseInputMap.values()) {
            if(p == Pulse.LOW) return false;
        }
        return true;
    }

    public void addInput(String input) {
        pulseInputMap.put(input, Pulse.LOW);
    }

}
