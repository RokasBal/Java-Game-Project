package utilz;

import entities.Player;
import main.GamePanel;
import main.LevelManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import main.LevelManager;

import static utilz.Constants.PlayerConstants.*;

/**
 * Klasė, naudojama tikrinti žaidėjo susidūrimą su žemėlapiu.
 * @author Rokas Baliutavičius, 5 grupė
 */

public class Collisions {
    private static int keyX;
    private static int keyY;
    private static float tempX = 0;
    private static float tempY;
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] layer1Data, int[][] levelData, int[][] layer3Data, Player player) {
        if(!IsSolid(x, y, layer1Data, levelData, layer3Data, player))
            if(!IsSolid(x + width, y + height, layer1Data, levelData, layer3Data, player))
                if(!IsSolid(x + width, y, levelData, layer1Data, layer3Data, player))
                    if(!IsSolid(x, y + height, levelData, layer1Data, layer3Data, player))
                        if(!IsSolid(x, y + height / 2, layer1Data, levelData, layer3Data, player))
                            if(!IsSolid(x + width, y + height / 2, layer1Data, levelData, layer3Data, player))
                                if(!IsSolid(x, y + height / 4, layer1Data, levelData, layer3Data, player))
                                    if(!IsSolid(x + width, y + height / 4, layer1Data, levelData, layer3Data, player))
                                        if(!IsSolid(x, y + height * 3 / 4, layer1Data, levelData, layer3Data, player))
                                            if(!IsSolid(x + width, y + height * 3 / 4, layer1Data, levelData, layer3Data, player))
                                                return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] layer1Data, int[][] levelData, int[][] layer3Data, Player player) {

        if(x < 0 || x >= Constants.mapInfo.mapWidth * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale)) return true;
        if(y < 0 || y >= Constants.mapInfo.mapHeight * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale)) return true;

        float xIndex = x / (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
        float yIndex = y / (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);

        int value = levelData[(int)yIndex][(int)xIndex];
        int specialsValue = layer1Data[(int)yIndex][(int)xIndex];

        //Key collection
        if(specialsValue == 88) {
            keyX = (int)xIndex;
            keyY = (int)yIndex;
            player.keyCollected = true;
            player.getGamePanel().getGame().getLevelManager().currentLevel.getLevel1Data()[keyY][keyX] = 0;
        }

        //Death condition
        if((value >= 143 && value <= 151) || (value >= 156 && value <= 167) || value == 109) {
            if(player.keyCollected) {
                player.keyCollected = false;
                player.getGamePanel().getGame().getLevelManager().currentLevel.getLevel1Data()[keyY][keyX] = 88;
            }
            player.hasDied = true;
            return true;
        }

        //Doors
        if((value == 103 || value == 116) && player.keyCollected) {
            player.getGamePanel().getGame().drawText = true;
        } else if(!(value == 103 || value == 116)) player.getGamePanel().getGame().drawText = false;

        //Collidable
        if((value >= 3 && value <= 6) || (value >= 40 && value <= 45) || value == 53 || value == 55
                || value == 56 || value == 58 || (value >= 66 && value <= 71) || (value >= 78 && value <= 80)
                || value == 85 || value == 87 || value == 89 || value == 90 || (value >= 91 && value <= 93)
                || value == 100 || value == 128 || value == 126){
            return true;
        } else return false;
    }

    public static void redrawKey(Player player) {
        player.getGamePanel().getGame().getLevelManager().currentLevel.getLevel1Data()[keyY][keyX] = 88;
    }

    public static float GetEntityXPositionByWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)(hitbox.x / (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale));

        if(xSpeed > 0) {
            int tileXPosition = currentTile * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
            int xOffeset = (int) ((int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - hitbox.width);
            return tileXPosition + xOffeset - 1;
        } else {
            return currentTile * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
        }
    }

    public static boolean checkIfAtDoor(GamePanel gamePanel) {
        float x = gamePanel.getGame().getPlayer().getHitbox().x / (Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
        float y = gamePanel.getGame().getPlayer().getHitbox().y / (Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
        int[][] levelData = gamePanel.getGame().getLevelManager().getCurrentLevel().getLevel1Data();
        int backgroundTile = levelData[(int)y][(int)x];
//        System.out.println(backgroundTile);
//        System.out.println("Checking for door");
        if((backgroundTile == 103 || backgroundTile == 116) && gamePanel.getGame().getPlayer().keyCollected) {
            return true;
        }
        else return false;
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

    public static boolean IsEntityOnGround(Rectangle2D.Float hitbox, int[][] layer1Data, int[][] levelData, int[][] layer2Data, Player player) {
        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, layer1Data, levelData, layer2Data, player)) {
            if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, layer1Data, levelData, layer2Data, player)) {
                return false;
            }
        }
        return true;
    }
}

