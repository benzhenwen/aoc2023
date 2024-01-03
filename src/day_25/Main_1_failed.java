package day_25;

import java.io.File;
import java.util.*;

public class Main_1_failed {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_25_data.txt"));

        ArrayList<Connection> connections = new ArrayList<>();
        while(s.hasNextLine()) {
            String line = s.nextLine();
            String root = line.substring(0, 3);
            String[] connectNames = line.split(" ");
            for(int i = 1; i < connectNames.length; i++) {
                connections.add(new Connection(root, connectNames[i]));
            }
        }

        int iteration = 0;

        while(true) {
            iteration++;
                System.out.println(iteration);

            ArrayList<Connection> connectionsClone = connectionsClone(connections);
            HashMap<String, Integer> weightsClone = createOnesMap(connections);
            reOrder(connectionsClone);

            karger(connectionsClone, weightsClone);

            if(connectionsClone.get(0).count <= 3) {
                reOrder(connectionsClone);
                cleanup(connectionsClone);

                System.out.println(connectionsClone);
                System.out.println(weightsClone);


                break;
            }

        }



    }

    public static void karger(ArrayList<Connection> input, HashMap<String, Integer> pointWeight) {
        reOrder(input);
        cleanup(input);
        // System.out.println(input);

        if(input.size() == 1) {
            // System.out.println("RETURN");
            return;
        }

        Connection toMerge = random(input);
        input.remove(toMerge);

        // System.out.println("merging " + toMerge);
        String toKeep = toMerge.a;
        String toReplace = toMerge.b;
        // System.out.println(toReplace + " -> " + toKeep);
        pointWeight.put(toKeep, pointWeight.get(toKeep)+pointWeight.get(toReplace));
        pointWeight.remove(toReplace);

        for(Connection c : input) {
            if (c.a.equals(toReplace)) {
                c.a = toKeep;
            }
            if (c.b.equals(toReplace)) {
                c.b = toKeep;
            }
        }

        // System.out.println(input);
        // System.out.println();
        karger(input, pointWeight);
    }

    public static void reOrder(Collection<Connection> input) {
        for(Connection c : input) {
            if(c.b.compareTo(c.a) < 0) {
                String temp = c.b;
                c.b = c.a;
                c.a = temp;
            }
        }
    }

    public static void cleanup(Collection<Connection> input) {
        ArrayList<Connection> inputArray = new ArrayList<>(input);
        for(int i = 0; i < inputArray.size(); i++) {
            for(int j = i+1; j < inputArray.size(); j++) {
                if(inputArray.get(i).equals(inputArray.get(j))) {
                    inputArray.get(i).count += inputArray.get(j).count;
                    inputArray.remove(j);
                    j--;
                }
            }
        }
        input.clear();
        input.addAll(inputArray);
    }

    public static boolean allSameConnection(Collection<Connection> input) {
        String a = null, b = null;
        for(Connection c : input) {
            if(a == null) {
                a = c.a;
                b = c.b;
            }
            else {
                if(
                        (!a.equals(c.a) || !a.equals(c.b))
                        && (!Objects.equals(b, c.a) || !Objects.equals(b, c.b))
                ) return false;
            }
        }
        return true;
    }

    public static <T> T random(Collection<T> coll) {
        int num = (int) (Math.random() * coll.size());
        for(T t: coll) if (--num < 0) return t;
        throw new AssertionError();
    }

    public static HashMap<String, Integer> createOnesMap(Collection<Connection> input) {
        HashMap<String, Integer> output = new HashMap<String, Integer>();
        for(Connection c : input) {
            output.put(c.a, 1);
            output.put(c.b, 1);
        }
        return output;
    }

    public static ArrayList<Connection> connectionsClone(ArrayList<Connection> input) {
        ArrayList<Connection> output = new ArrayList<>();
        for(Connection i : input) {
            output.add(i.clone());
        }
        return output;
    }

}
