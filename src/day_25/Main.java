package day_25;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_25_data.txt"));

        HashMap<String, Component> components = new HashMap<>();
        while(s.hasNextLine()) {
            String name = s.nextLine().substring(0, 3);
            components.put(name, new Component(name));
        }

        s = new Scanner(new File("data/puzzle_25_data.txt"));
        while(s.hasNextLine()) {
            String line = s.nextLine();
            String name = line.substring(0, 3);
            String[] others = line.split(" ");

            Component c = components.get(name);
            for(int i = 1; i < others.length; i++) {
                Component other = components.get(others[i]);
                if(other == null) {
                    other = new Component(others[i]);
                    components.put(other.name, other);
                }
                c.createLink(other);
            }
        }
        s.close();



        while(true) {
            for(Component c : components.values()) {
                new SuperNode(c);
            }

            while (SuperNode.allNodes.size() > 2) {
                SuperNode randomNodeToMerge = SuperNode.allNodes.get(random(SuperNode.allNodes.keySet()));
                SuperNode randomNodeToMergeWith = SuperNode.allNodes.get(random(randomNodeToMerge.links));

                randomNodeToMerge.merge(randomNodeToMergeWith);
            }

            if(SuperNode.allNodes.values().toArray(new SuperNode[0])[0].links.size() == 3) {
                break;
            }

            SuperNode.allNodes.clear();

        }

        System.out.println("number of super nodes: " + SuperNode.allNodes.size());

        int output = 1;
        for(SuperNode n : SuperNode.allNodes.values()) {
            System.out.println("connections: " + n.links.size());
            System.out.println("size: " + n.size);
            output *= n.size;
        }
        System.out.println("output: " + output);

    }

    public static <T> T random(Collection<T> coll) {
        int num = (int) (Math.random() * coll.size());
        for(T t: coll) if (--num < 0) return t;
        throw new AssertionError();
    }
}
