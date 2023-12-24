package day_22;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_22_data.txt"));

        ArrayList<Block> blocks = new ArrayList<>();

        while(s.hasNextLine()) {
            blocks.add(new Block(s.nextLine()));
        }

        boolean hasFall = true;
        while(hasFall) {
            hasFall = false;

            for(Block b : blocks) {
                b.moveDown();
                if(b.end.z < 1 || b.start.z < 1 || blockIntersectsAny(b, blocks)) b.moveUp();
                else hasFall = true;
            }
        }

        HashMap<Block, HashSet<Block>> supporting = new HashMap<>();
        HashMap<Block, HashSet<Block>> supporters = new HashMap<>();
        for(Block b : blocks) {
            supporting.put(b, new HashSet<>());
            supporters.put(b, new HashSet<>());
        }

        for(Block b : blocks) {
            for(Block other : blocks) {
                if(b != other) {
                    if(other.isCarrying(b)) {
                        supporting.get(other).add(b); // other is supporting b
                        supporters.get(b).add(other); // b is being supported by other
                    }
                }
            }
        }

        int output = 0;
        for(Block b : blocks) {
            boolean canDestroy = true;
            for(Block potentialFaller : supporting.get(b)) {
                if(supporters.get(potentialFaller).size() <= 1) {
                    canDestroy = false;
                    break;
                }
            }
            if(canDestroy) output++;
        }
        System.out.println(output);

    }

    public static boolean blockIntersectsAny(Block b, Collection<Block> blocks) {
        for(Block other : blocks) {
            if(b.intersects(other) && b != other) return true;
        }
        return false;
    }

}
