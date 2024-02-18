package utilz;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerInfo {
        public static final int WIDTH = 20;
        public static final int HEIGHT = 38;
        public static final int SPRITE_WIDTH = 120;
        public static final int SPRITE_HEIGHT = 80;
        public static final int START_X = 550;
        public static final int START_Y = 700;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int JUMPING_RIGHT = 1;
        public static final int JUMPING_LEFT = 2;
        public static final int RUNNING_RIGHT = 3;
        public static final int RUNNING_LEFT = 4;
        public static final int FALLING_RIGHT = 5;
        public static final int DYING = 6;
        public static final int FALLING_LEFT = 7;


        public static int getSpriteAmount(int playerAction) {
            return switch (playerAction) {
                case IDLE, RUNNING_LEFT, RUNNING_RIGHT, DYING -> 10;
                case JUMPING_RIGHT, JUMPING_LEFT, FALLING_RIGHT, FALLING_LEFT -> 3;
                default -> 1;
            };
        }
    }

    public static class mapInfo {
        public static final int mapWidth = 50;
        public static final int mapHeight = 40;

        public static final int tileSize = 32;
        public static final float gameScale = 1.5f;
        public static final int visibleTilesX = 20;
        public static final int visibleTilesY = 14;
        public static final int visibleWidth =  20 * (int) (tileSize * gameScale);
        public static final int visibleHeight = 14 * (int) (tileSize * gameScale);
    }

    public static class physicsControl {
        public static final float gravity = 0.05f;
        public static final float jumpSpeed = -3.2f;
        public static final float fallSpeedAfterCollision = 0.5f;
        public static final float playerSpeed = 1.5f;
    }
}
