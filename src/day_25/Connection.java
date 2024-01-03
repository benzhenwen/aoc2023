package day_25;

import java.util.Objects;

public class Connection {

    String a, b;
    int count;

    public Connection(String a, String b) {
        this.a = a;
        this.b = b;
        this.count = 1;
    }

    public String toString() {
        return a + " - " + b + " #" + count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(a, that.a) && Objects.equals(b, that.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    public Connection clone() {
        Connection output = new Connection(a, b);
        output.count = count;
        return output;
    }
}
