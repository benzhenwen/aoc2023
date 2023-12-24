package day_20;

public class FlipFlop extends Module {

    public FlipFlop(String name, String[] destinations) {super(name, destinations);}

    private boolean status = false;

    public PulseEvent[] run(String from, Pulse p) {
        if(p == Pulse.LOW) {
            status = !status;
            Pulse outputPulse = status ? Pulse.HIGH : Pulse.LOW;
            PulseEvent[] output = new PulseEvent[destinations.length];
            for(int i = 0; i < destinations.length; i++) {
                output[i] = new PulseEvent(this.name, destinations[i], outputPulse);
            }

            return output;
        }
        return new PulseEvent[0];
    }

}
