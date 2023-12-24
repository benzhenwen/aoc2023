package day_20;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_20_data.txt"));

        HashMap<String, Module> moduleMap = new HashMap<>();

        while(s.hasNextLine()) {
            Module newModule = Module.createModule(s.nextLine());
            moduleMap.put(newModule.name, newModule);
        }

        Module.linkConjunctionModules(moduleMap.values());

        Queue<PulseEvent> queue = new LinkedList<>();

        for(int i = 0; i < 1000; i++) {
            queue.add(new PulseEvent("button", "broadcaster", Pulse.LOW));
            while (!queue.isEmpty()) {
                PulseEvent e = queue.remove();
                if(moduleMap.containsKey(e.to)) queue.addAll(Arrays.asList(moduleMap.get(e.to).run(e.from, e.type)));
                Module.logPulse(e.type);
            }
        }

        System.out.println(Module.lowPulsesSent + " " + Module.highPulsesSent);
        System.out.println(Module.lowPulsesSent * Module.highPulsesSent);

    }

}
