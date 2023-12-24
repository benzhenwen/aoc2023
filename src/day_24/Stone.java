package day_24;

public class Stone {

    public Triple position, velocity;

    public Stone(String input) {
        String[] positions = input.split("@")[0].split(",");
        String[] velocities = input.split("@")[1].split(",");

        position = new Triple(
                Long.parseLong(positions[0].trim()),
                Long.parseLong(positions[1].trim()),
                Long.parseLong(positions[2].trim())
        );
        velocity = new Triple(
                Long.parseLong(velocities[0].trim()),
                Long.parseLong(velocities[1].trim()),
                Long.parseLong(velocities[2].trim())
        );
    }

}
