package day_20;

import java.io.File;
import java.util.*;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_20_data.txt"));

        HashMap<String, Module> moduleMap = new HashMap<>();

        while(s.hasNextLine()) {
            Module newModule = Module.createModule(s.nextLine());
            moduleMap.put(newModule.name, newModule);
        }

        Module.linkConjunctionModules(moduleMap.values());

        Queue<PulseEvent> queue = new LinkedList<>();

        int buttonPresses = 0;

        while(buttonPresses < 10000) {
            buttonPresses++;

            queue.add(new PulseEvent("button", "broadcaster", Pulse.LOW));
            while (!queue.isEmpty()) {
                PulseEvent e = queue.remove();
                if(moduleMap.containsKey(e.to)) queue.addAll(Arrays.asList(moduleMap.get(e.to).run(e.from, e.type)));

                if(e.from.equals("hf" /* sb, nd, ds, hs for my example, which are all part of the conjunction zp which links to rx*/ ) && e.type == Pulse.HIGH) {
                    System.out.println(buttonPresses);
                }
            }
        }

        // cycle length of sb being true is 3797
        // cycle length of nd being true is 3917
        // cycle length of ds being true is 3733
        // cycle length of hf being true is 3877

        // lcm of 3797 3917 3733 3877 is 215252378794009 (answer)

    }

}
