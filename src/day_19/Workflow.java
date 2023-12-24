package day_19;

public class Workflow {

    public String name;
    public Condition[] conditions;
    public String[] outputs;
    public String defaultOutput;

    public Workflow(String input) {
        this.name = input.split("\\{")[0];

        String[] parts = input.split("\\{")[1].substring(0, input.split("\\{")[1].length()-1).split(",");
        conditions = new Condition[parts.length-1];
        outputs = new String[parts.length-1];

        for(int i = 0; i < parts.length-1; i++) {
            String[] part = parts[i].split(":");
            conditions[i] = new Condition(
                    part[0].charAt(0),
                    part[0].charAt(1) == '>',
                    Integer.parseInt(part[0].substring(2))
            );
            outputs[i] = part[1];
        }

        defaultOutput = parts[parts.length-1];
    }

    public String run(Party p) {
        for(int i = 0; i < conditions.length; i++) {
            if(p.meets(conditions[i])) return outputs[i];
        }
        return defaultOutput;
    }

}
