package day_25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SuperNode {

    public static HashMap<String, SuperNode> allNodes = new HashMap<>();

    public ArrayList<String> links;
    public int size;
    public String name;

    public SuperNode(Component c) {
        links = new ArrayList<>();
        for(Component linkC : c.links) {
            links.add(linkC.name);
        }
        size = 1;
        name = c.name;
        allNodes.put(c.name, this);
    }

    public void merge(SuperNode other) {
        // System.out.println(allNodes);

        // System.out.println("merge " + this.name + " <- " + other.name);

        this.size += other.size;

        this.links.removeIf(e -> e.equals(other.name));

        for(String link : other.links) {
            if(!link.equals(this.name)) {
                this.links.add(link);
            }
        }

        for(int i = 0; i < links.size(); i++) {
            SuperNode s = allNodes.get(links.get(i));
            if(s.links.contains(other.name)) {
                s.links.replaceAll(e -> e.equals(other.name) ? this.name : e);
            }
        }

        allNodes.remove(other.name);

        // System.out.println(allNodes);

        // System.out.println();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String s : links) {
            sb.append(s).append(' ');
        }
        return sb.toString();
    }

}
