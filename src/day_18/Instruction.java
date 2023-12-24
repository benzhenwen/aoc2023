package day_18;

import day_18.Main.Direction;

import java.awt.*;

public class Instruction {

    public Direction d;
    public int length;

    public Instruction(Direction d, int length) {
        this.d = d;
        this.length = length;
    }

    public void run(Space[][] map, Position currentPosition) {
        for(int i = 0; i < length; i++) {
            Space s = map[(int) currentPosition.row][(int) currentPosition.col];
            s.isDug = true;
            s.isColored = true;
            currentPosition.move(d);
        }
    }

}
