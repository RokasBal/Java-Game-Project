package utilz;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerInfo {
        public static final int WIDTH = 96;
        public static final int HEIGHT = 96;
        public static final int START_X = 96;
        public static final int START_Y = 425;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING_RIGHT = 1;
        public static final int RUNNING_LEFT = 2;

        public static int getSpriteAmount(int playerAction) {
            return switch (playerAction) {
                case IDLE -> 9;
                case RUNNING_LEFT, RUNNING_RIGHT -> 4;
                default -> 1;
            };
        }
    }
}
