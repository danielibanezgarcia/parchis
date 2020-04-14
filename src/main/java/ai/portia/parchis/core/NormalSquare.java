package ai.portia.parchis.core;

public class NormalSquare extends AbstractSquare implements Square {

    private static final int RAMP_INDEX = 0;
    private static final int START_INDEX = 5;
    private static final int PROTECTED_INDEX = 12;

    private final int indexInColor;
    private final String displayId;
    private final boolean rampEntrance;
    private final boolean start;
    private final boolean colorProtected;
    private NormalSquare next;
    private NormalSquare previous;

    public NormalSquare(int indexInColor, String displayId, Color color) {
        super(color);
        this.indexInColor = indexInColor;
        this.displayId = displayId;
        this.rampEntrance = this.indexInColor == RAMP_INDEX;
        this.start = this.indexInColor == START_INDEX;
        this.colorProtected = this.indexInColor == PROTECTED_INDEX;
        this.next = null;
        this.previous = null;
    }

    public int getIndexInColor() {
        return indexInColor;
    }

    public String getDisplayId() {
        return displayId;
    }

    public boolean isRampEntrance() {
        return rampEntrance;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isColorProtected() {
        return colorProtected;
    }

    public void setNext(final NormalSquare next) {
        if (this.next != null) {
            throw new IllegalArgumentException("Next position was already set");
        }
        this.next = next;
    }

    public void setPrevious(final NormalSquare previous) {
        if (this.previous != null) {
            throw new IllegalArgumentException("Previous position was already set");
        }
        this.previous = previous;
    }

    public NormalSquare getNext() {
        return next;
    }

    public NormalSquare getPrevious() {
        return previous;
    }

    @Override
    public String toString() {
        return "NormalSquare{" +
                "color=" + super.getColor() +
                ", indexInColor=" + indexInColor +
                ", displayId='" + displayId + "'" +
                ", rampEntrance=" + rampEntrance +
                ", start=" + start +
                ", colorProtected=" + colorProtected +
                ", next=" + (next != null ? next.displayId : "") +
                ", previous=" + (previous != null ? previous.displayId : "") +
                "} ";
    }
}
