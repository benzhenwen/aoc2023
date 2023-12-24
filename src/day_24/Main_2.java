package day_24;

import javax.naming.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main_2 {

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(new File("data/puzzle_24_data.txt"));

        ArrayList<Stone> stones = new ArrayList<>();
        while(s.hasNextLine()) {
            stones.add(new Stone(s.nextLine()));
        }

        StringBuilder out = new StringBuilder("Solve[");
        for(int i = 0; i < 3; i++) {
            out.append("x+vx*t"+i+"==");
            out.append((long) stones.get(i).position.x);
            out.append("+(");
            out.append((long) stones.get(i).velocity.x);
            out.append("*t"+i+") && ");

            out.append("y+vy*t"+i+"==");
            out.append((long) stones.get(i).position.y);
            out.append("+(");
            out.append((long) stones.get(i).velocity.y);
            out.append("*t"+i+") && ");

            out.append("z+vz*t"+i+"==");
            out.append((long) stones.get(i).position.z);
            out.append("+(");
            out.append((long) stones.get(i).velocity.z);
            out.append("*t"+i+") && ");
        }

        System.out.println(out.substring(0, out.length()-3) + ",Integers]");

        // from Mathematica
        // x -> 118378223846841, y -> 228996474589321, z -> 259397320329497

        System.out.println(118378223846841L + 228996474589321L + 259397320329497L);

    }

    /*
    x + vx * t == hailstone_x + v_hailstone_x * t
    y + vy * t == hailstone_y + v_hailstone_y * t
    z + vz * t == hailstone_z + v_hailstone_z * t
     */



}
