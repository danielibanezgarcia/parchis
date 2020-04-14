package ai.portia.parchis.dice;

public enum DiceSides {

        SIX(6);

        private final int value;

        DiceSides(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
