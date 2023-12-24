package day_3;

public class Cell {

    private boolean isInteger;
    private char symbol;

    public Cell(char value) {
        try{
            Integer.parseInt(String.valueOf(value));
            symbol = value;
            isInteger = true;
        }
        catch (Exception ignored) {
            symbol = value;
            isInteger = false;
        }
    }

    public boolean isInteger() {
        return isInteger;
    }

    public boolean isEmpty() {
        return symbol == '.';
    }

    public boolean isSymbol() {
        return !isInteger && !isEmpty();
    }

    public char getSymbol() {
        return symbol;
    }

}
