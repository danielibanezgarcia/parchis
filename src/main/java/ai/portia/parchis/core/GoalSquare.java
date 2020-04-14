package ai.portia.parchis.core;

public class GoalSquare extends AbstractSquare implements Square {

    public GoalSquare(final Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "GoalSquare{" +
                "color=" + super.getColor() +
                '}';
    }
}
