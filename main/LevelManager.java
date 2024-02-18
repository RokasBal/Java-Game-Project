package main;

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
    private Level currentLevel, levelOne, levelTwo;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(ParseJSON.readFromJson("tutorial2.json", 0), ParseJSON.readFromJson("tutorial2.json", 1));
        //levelTwo = new Level(ParseJSON.readFromJson("test_dungeon.json", 0), ParseJSON.readFromJson("test_dungeon.json", 1));
        currentLevel = levelOne;
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

    public void draw(Graphics g) {
        int l1index = 0, l2index = 0;
        for (int i = 0; i < Constants.mapInfo.mapHeight; i++) {
            for (int j = 0; j < Constants.mapInfo.mapWidth; j++) {
                g.drawImage(levelSprite[32], j * Constants.mapInfo.tileSize, i * Constants.mapInfo.tileSize, null);
                l1index = levelOne.getLayer1Index(i, j) - 1;
                l2index = levelOne.getLayer2Index(i, j) - 1;
                g.drawImage(levelSprite[l1index], j * Constants.mapInfo.tileSize, i * Constants.mapInfo.tileSize, Constants.mapInfo.tileSize, Constants.mapInfo.tileSize, null);
                g.drawImage(levelSprite[l2index], j * Constants.mapInfo.tileSize, i * Constants.mapInfo.tileSize, Constants.mapInfo.tileSize, Constants.mapInfo.tileSize, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }

}
