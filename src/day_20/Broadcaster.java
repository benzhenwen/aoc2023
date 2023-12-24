package day_20;

public class Broadcaster extends Module {

    public Broadcaster(String name, String[] destinations) {super(name, destinations);}

    public PulseEvent[] run(String from, Pulse p) {
        PulseEvent[] output = new PulseEvent[destinations.length];
        for(int i = 0; i < destinations.length; i++) {
            output[i] = new PulseEvent(this.name, destinations[i], p);
        }

        return output;
    }

}
