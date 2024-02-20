package main;

import entities.Player;
import utilz.Constants;
import utilz.LoadImages;
import main.ParseJSON.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    private final Game game;
    //private int levelArraySizeX = 10, levelArraySizeY = 7;
    private BufferedImage[] levelSprite;
    //BufferedImage img2 = LoadImages.GetSpriteImage(LoadImages.BACKGROUND_IMAGE);
    private BufferedImage backgroundImage;
    private static int[][] array;
    private static Level currentLevel;
    private Level tutorial, levelOne;
    private Level levelTwo;
    private Level testlevel;
    public boolean keyCollected = false;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        //levelOne = new Level(ParseJSON.readFromJson("tutorial2.json", 0, levelOne), ParseJSON.readFromJson("tutorial2.json", 1, levelOne));
        tutorial = new Level(ParseJSON.readFromJson("tutorial.json", 0), ParseJSON.readFromJson("tutorial.json", 1));
        testlevel = new Level(ParseJSON.readFromJson("test_export.json", 0), ParseJSON.readFromJson("test_export.json", 1));
        //levelTwo = new Level(ParseJSON.readFromJson("test_dungeon.json", 0), ParseJSON.readFromJson("test_dungeon.json", 1));
        currentLevel = testlevel;
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadImages.GetSpriteImage(LoadImages.TILESET_FILE);
        levelSprite = new BufferedImage[(img.getWidth() * img.getHeight()) / Constants.mapInfo.tileSize];
        for(int j = 0; j < (img.getHeight() / Constants.mapInfo.tileSize); j++) {
            for(int i = 0; i < (img.getWidth() / Constants.mapInfo.tileSize); i++) {
                int index = j * (img.getWidth() / Constants.mapInfo.tileSize) + i;
                levelSprite[index] = img.getSubimage(i * Constants.mapInfo.tileSize, j * Constants.mapInfo.tileSize, Constants.mapInfo.tileSize, Constants.mapInfo.tileSize);
            }
        }
    }

    public void draw(Graphics g, int levelOffsetX, int levelOffsetY, Player player) {
        int l1index = 0, l2index = 0;
        for(int i = 0; i < Constants.mapInfo.mapHeight * 4; i++) {
            for (int j = 0; j < Constants.mapInfo.mapWidth * 4; j++) {
                g.drawImage(levelSprite[54], j * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetX, i * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), null);
            }
        }
        for (int i = 0; i < Constants.mapInfo.mapHeight; i++) {
            for (int j = 0; j < Constants.mapInfo.mapWidth; j++) {
//                g.drawImage(levelSprite[32], j * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetX, i * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), null);
                l1index = currentLevel.getLayer1Index(i, j);
                l2index = currentLevel.getLayer2Index(i, j);
                g.drawImage(levelSprite[l1index], j * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetX, i * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), null);
                g.drawImage(levelSprite[l2index], j * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetX, i * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), null);
            }
        }
    }

    public void update() {

    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }

}
