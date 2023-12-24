package day_19;

import java.io.File;
import java.util.*;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_19_data.txt"));

        HashMap<String, Workflow> workflows = new HashMap<>();

        while(s.hasNextLine()) {
            String line = s.nextLine();
            if(line.isBlank()) break;
            workflows.put(line.split("\\{")[0], new Workflow(line));
        }

        Queue<DynamicParty> queue = new LinkedList<>();
        queue.add(new DynamicParty("in",
                new TraitRange(1, 4000),
                new TraitRange(1, 4000),
                new TraitRange(1, 4000),
                new TraitRange(1, 4000)
        ));

        long output = 0;

        while(!queue.isEmpty()) {
            DynamicParty toProcess = queue.remove();
            Collection<DynamicParty> newParties = toProcess.split(workflows.get(toProcess.location));
            for(DynamicParty p : newParties) {
                if(p.location.equals("A")) { // add to the count
                    output += p.getAmountRepresented();
                }
                else if(p.location.equals("R")) {
                    // do nothing
                }
                else { // to more work!
                    queue.add(p);
                }
            }
        }

        System.out.println(output);

    }
}
