package utilz;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
        public static final int JUMPING = 4;
        public static final int JUMPING_LEFT = 5;
        public static final int JUMPING_RIGHT = 6;
        public static final int FALLING = 7;
    }

    public static class PlayerInfo {
        public static final int WIDTH = 96;
        public static final int HEIGHT = 96;
        public static final int START_X = 180;
        public static final int START_Y = 450;
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

    public static class mapInfo {
        public static final int mapWidth = 50;
        public static final int mapHeight = 40;
        public static final int tileSize = 32;
    }

    public static class physicsControl {
        public static final float gravity = 0.08f;
        public static final float jumpSpeed = -4.5f;
        public static final float fallSpeedAfterCollision = 1f;
    }
}
