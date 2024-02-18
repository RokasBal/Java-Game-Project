package utilz;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Collisions {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if(!IsSolid(x, y, levelData))
            if(!IsSolid(x + width, y + height, levelData))
                if(!IsSolid(x + width, y, levelData))
                    if(!IsSolid(x, y + height, levelData))
                        if(!IsSolid(x, y + height / 2, levelData))
                            if(!IsSolid(x + width, y + height / 2, levelData))
                                if(!IsSolid(x, y + height / 4, levelData))
                                    if(!IsSolid(x + width, y + height / 4, levelData))
                                        if(!IsSolid(x, y + height * 3 / 4, levelData))
                                            if(!IsSolid(x + width, y + height * 3 / 4, levelData))
                                                return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        if(x < 0 || x >= Constants.mapInfo.mapWidth * Constants.mapInfo.tileSize) return true;
        if(y < 0 || y >= Constants.mapInfo.mapHeight * Constants.mapInfo.tileSize) return true;

        float xIndex = x / Constants.mapInfo.tileSize;
        float yIndex = y / Constants.mapInfo.tileSize;

        int value = levelData[(int)yIndex][(int)xIndex];

        if((value > 6 && value < 120) || (value > 135 && value < 140) || (value > 150 && value < 155) || (value > 210 && value <= 270)){
            return true;
        } else return false;
    }

    public static float GetEntityXPositionByWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)(hitbox.x / Constants.mapInfo.tileSize);

        if(xSpeed > 0) {
            int tileXPosition = currentTile * Constants.mapInfo.tileSize;
            int xOffeset = (int) (Constants.mapInfo.tileSize - hitbox.width);
            //System.out.println(hitbox.width);
            return tileXPosition + xOffeset - 1;
        } else {
            return currentTile * Constants.mapInfo.tileSize;
        }
    }

    public static float GetEntityYPositionByGround(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int)(hitbox.y / Constants.mapInfo.tileSize);

        if(airSpeed > 0) {
            int tileYPosition = currentTile * Constants.mapInfo.tileSize;
            int yOffset = (int) (Constants.mapInfo.tileSize - hitbox.height);
            return tileYPosition + yOffset - 1;
        } else {
            return currentTile * Constants.mapInfo.tileSize;
        }
    }

    public static boolean IsEntityOnGround(Rectangle2D.Float hitbox, int[][] levelData) {
        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1 , levelData)) {
            if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData)) {
                return false;
            }
        }
        return true;
    }
}
