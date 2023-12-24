package day_19;

public class Party {

    public int x, m, a, s;

    public Party(String input) {
        String[] parts = input.substring(1, input.length()-1).split(",");
        this.x = Integer.parseInt(parts[0].substring(2));
        this.m = Integer.parseInt(parts[1].substring(2));
        this.a = Integer.parseInt(parts[2].substring(2));
        this.s = Integer.parseInt(parts[3].substring(2));
    }

    public Party(int x, int m, int a, int s) {
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
    }

    public boolean meets(Condition c) {
        int checkValue = -1;
        switch(c.trait) {
            case 'x' -> checkValue = x;
            case 'm' -> checkValue = m;
            case 'a' -> checkValue = a;
            case 's' -> checkValue = s;
        }
        if(c.condition) return checkValue > c.value;
        else return checkValue < c.value;
    }

    public int getRating() {
        return x + m + a + s;
    }

}
