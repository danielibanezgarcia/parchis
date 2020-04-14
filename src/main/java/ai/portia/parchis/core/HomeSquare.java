package ai.portia.parchis.core;

public class HomeSquare extends AbstractSquare implements Square {

    public HomeSquare(final Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "HomeSquare{" +
                "color=" + super.getColor() +
                '}';
    }
}
