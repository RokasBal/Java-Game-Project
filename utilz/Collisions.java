package utilz;

import entities.Player;
import main.GamePanel;
import main.LevelManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import main.LevelManager;

import static utilz.Constants.PlayerConstants.*;


public class Collisions {
    private static int keyX;
    private static int keyY;
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData, int[][] layer2Data, Player player) {
        if(!IsSolid(x, y, levelData, layer2Data, player))
            if(!IsSolid(x + width, y + height, levelData, layer2Data, player))
                if(!IsSolid(x + width, y, levelData, layer2Data, player))
                    if(!IsSolid(x, y + height, levelData, layer2Data, player))
                        if(!IsSolid(x, y + height / 2, levelData, layer2Data, player))
                            if(!IsSolid(x + width, y + height / 2, levelData, layer2Data, player))
                                if(!IsSolid(x, y + height / 4, levelData, layer2Data, player))
                                    if(!IsSolid(x + width, y + height / 4, levelData, layer2Data, player))
                                        if(!IsSolid(x, y + height * 3 / 4, levelData, layer2Data, player))
                                            if(!IsSolid(x + width, y + height * 3 / 4, levelData, layer2Data, player))
                                                return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData, int[][] layer2Data, Player player) {
        if(x < 0 || x >= Constants.mapInfo.mapWidth * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale)) return true;
        if(y < 0 || y >= Constants.mapInfo.mapHeight * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale)) return true;

        float xIndex = x / (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
        float yIndex = y / (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);

        int value = levelData[(int)yIndex][(int)xIndex];
        int specialsValue = layer2Data[(int)yIndex][(int)xIndex];

        if(specialsValue == 147) {
            keyX = (int)xIndex;
            keyY = (int)yIndex;
            player.keyCollected = true;
            layer2Data[(int)yIndex][(int)xIndex] = 4;
        }

        if((value >= 212 && value <= 217) || (value >= 233 && value <= 240) || value == 254 || value == 255 || value == 267 || value == 270) {
            if(player.keyCollected) {
                player.keyCollected = false;
                layer2Data[keyY][keyX] = 147;
            }
            player.hasDied = true;
            return true;
        }

        if(value == 164 || value == 179) {
            if(player.keyCollected) {
                System.out.println("Open door");
            }
        }

        if((value > 6 && value < 120) || (value > 135 && value < 140) || (value > 150 && value < 155) || (value > 210 && value <= 270)){
            return true;
        } else return false;
    }

    public static float GetEntityXPositionByWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)(hitbox.x / (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale));

        if(xSpeed > 0) {
            int tileXPosition = currentTile * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
            int xOffeset = (int) ((int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - hitbox.width);
            //System.out.println(hitbox.width);
            return tileXPosition + xOffeset - 1;
        } else {
            return currentTile * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
        }
    }

    public static float GetEntityYPositionByGround(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int)(hitbox.y / (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale));

        if(airSpeed > 0) {
            int tileYPosition = currentTile * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
            int yOffset = (int)((int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - hitbox.height);
            return tileYPosition + yOffset - 1;
        } else {
            return currentTile * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
        }
    }

    public static boolean IsEntityOnGround(Rectangle2D.Float hitbox, int[][] levelData, int[][] layer2Data, Player player) {
        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1 , levelData, layer2Data, player)) {
            if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData, layer2Data, player)) {
                return false;
            }
        }
        return true;
    }
}
