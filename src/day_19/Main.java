package day_19;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_19_data.txt"));

        HashMap<String, Workflow> workflows = new HashMap<>();
        ArrayList<Party> parties = new ArrayList<>();

        boolean seenBlank = false;
        while(s.hasNextLine()) {
            String line = s.nextLine();
            if(line.isBlank()) seenBlank = true;
            else if(!seenBlank) {
                workflows.put(line.split("\\{")[0], new Workflow(line));
            }
            else {
                parties.add(new Party(line));
            }
        }

        int output = 0;

        for(Party party : parties) {

            String currentStatus = "in";
            while(!(currentStatus.equals("A") || currentStatus.equals("R"))) {
                currentStatus = workflows.get(currentStatus).run(party);
            }
            if(currentStatus.equals("A")) {
                output += party.getRating();
            }

        }

        System.out.println(output);

    }
}
