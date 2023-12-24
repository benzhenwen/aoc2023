package day_19;

public class Condition {

    public char trait;
    public boolean condition;
    public int value;

    /**
     * creates a condition
     * @param trait x, m, a, s
     * @param condition false: less than, true: greater than
     * @param value value to compare
     */
    public Condition(char trait, boolean condition, int value) {
        this.trait = trait;
        this.condition = condition;
        this.value = value;
    }

}
