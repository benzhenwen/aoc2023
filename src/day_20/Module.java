package day_20;

import java.util.Collection;
import java.util.HashMap;

public abstract class Module {

    public static long highPulsesSent = 0;
    public static long lowPulsesSent = 0;


    public String name;
    public String[] destinations;

    public Module(String name, String[] destinations) {
        this.name = name;
        this.destinations = destinations;
    }

    public abstract PulseEvent[] run(String from, Pulse p);

    public static Module createModule(String input) {
        String[] destinations = null;
        destinations = input.split(">")[1].split(",");
        for(int i = 0; i < destinations.length; i++) {
            destinations[i] = destinations[i].trim();
        }
        String name = input.split(" ")[0].substring(1);

        switch(input.charAt(0)) {
            case 'b':
                return new Broadcaster("broadcaster", destinations);
            case '%':
                return new FlipFlop(name, destinations);
            case '&':
                return new Conjunction(name, destinations);
        }

        throw new RuntimeException("could not create module");
    }

    public static void linkConjunctionModules(Collection<Module> modules) {
        HashMap<String, Conjunction> conjunctionModules = new HashMap<>();
        for(Module m : modules) {
            if(m.getClass() == Conjunction.class) {
                conjunctionModules.put(m.name, (Conjunction) m);
            }
        }
        for(Module m : modules) {
            for(String s : m.destinations) {
                if(conjunctionModules.containsKey(s)) {
                    conjunctionModules.get(s).addInput(m.name);
                }
            }
        }
    }

    public static void logPulse(Pulse p) {
        if(p == Pulse.LOW) lowPulsesSent++;
        else highPulsesSent++;
    }

}
