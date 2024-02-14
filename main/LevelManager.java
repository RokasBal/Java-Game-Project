package main;

import utilz.Constants;
import utilz.LoadImages;
import main.ParseJSON.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    private final Game game;
    private int levelArraySizeX = 10, levelArraySizeY = 7;
    private BufferedImage[] levelSprite;
    BufferedImage img2 = LoadImages.GetSpriteImage(LoadImages.BACKGROUND_IMAGE);
    private BufferedImage backgroundImage;
    private static int[][] array;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        //array = ParseJSON.readFromJson();
        //levelSprite = LoadImages.GetSpriteImage(LoadImages.TILESET_FILE);
        importOutsideSprites();
        levelOne = new Level(ParseJSON.readFromJson());
        //array = levelOne.getLevelData();
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadImages.GetSpriteImage(LoadImages.TILESET_FILE);
        levelSprite = new BufferedImage[levelArraySizeX*levelArraySizeY];
        for(int j = 0; j < levelArraySizeY; j++) {
            for(int i = 0; i < levelArraySizeX; i++) {
                int index = j * levelArraySizeX + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g) {
//        g.drawImage(levelSprite[11], 0, 0, null);
        int index = 0;
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 35; j++) {
                g.drawImage(img2, j * 32, i * 32, null);
                index = levelOne.getSpriteIndex(i, j) - 1;
                g.drawImage(levelSprite[index], j * Constants.mapInfo.tileSize, i * Constants.mapInfo.tileSize, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }

}
