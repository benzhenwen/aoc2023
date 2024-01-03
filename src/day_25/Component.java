package day_25;

import day_23.Intersection;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Component {

    public String name;
    public HashSet<Component> links;

    public Component(String name) {
        this.name = name;
        this.links = new HashSet<>();
    }

    public void createLink(Component other) {
        this.links.add(other);
        other.links.add(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(" -> ");
        for(Component c : links) {
            sb.append(c.name);
            sb.append(' ');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(name, component.name) && Objects.equals(links, component.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
