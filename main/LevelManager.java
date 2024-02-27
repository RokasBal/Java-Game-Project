package main;

import entities.Player;
import utilz.Constants;
import utilz.LoadImages;
import main.ParseJSON.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

    private final Game game;
    //private int levelArraySizeX = 10, levelArraySizeY = 7;
    private BufferedImage[] levelSprite;
    //BufferedImage img2 = LoadImages.GetSpriteImage(LoadImages.BACKGROUND_IMAGE);
    private BufferedImage backgroundImage;
    private static Level currentLevelStatic;
    private static int[][] array;
    public  Level currentLevel;
    private Level tutorial, levelOne;
    private Level levelTwo, levelThree;
    private Level testlevel;
    public boolean keyCollected = false;
    public ArrayList<Level> levels = new ArrayList<>();
    private int index = 1;

    /**
     * Klasė, vykdanti lygių piešimą bei keitimą.
     * @author Rokas Baliutavičius, 5 grupė
     */

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        tutorial = new Level(ParseJSON.readFromJson("tutorial.json", 0), ParseJSON.readFromJson("tutorial.json", 1), ParseJSON.readFromJson("tutorial.json", 2));
        levels.add(tutorial);
        levelTwo = new Level(ParseJSON.readFromJson("levelTwo.json", 0), ParseJSON.readFromJson("levelTwo.json", 1), ParseJSON.readFromJson("levelTwo.json", 2));
        levels.add(levelTwo);
        levelThree = new Level(ParseJSON.readFromJson("level2.json", 0), ParseJSON.readFromJson("level2.json", 1), ParseJSON.readFromJson("level2.json", 2));
        levels.add(levelThree);
        currentLevel = tutorial;
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
        int l1index = 0, l2index = 0, l3index = 0;
        for(int i = 0; i < Constants.mapInfo.mapHeight * 4; i++) {
            for (int j = 0; j < Constants.mapInfo.mapWidth * 4; j++) {
                g.drawImage(levelSprite[54], j * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetX, i * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), null);
            }
        }
        for (int i = 0; i < Constants.mapInfo.mapHeight; i++) {
            for (int j = 0; j < Constants.mapInfo.mapWidth; j++) {
                l1index = currentLevel.getLayer1Index(i, j);
                l2index = currentLevel.getLayer2Index(i, j);
                g.drawImage(levelSprite[l1index], j * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetX, i * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), null);
                g.drawImage(levelSprite[l2index], j * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetX, i * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), null);
            }
        }
    }

    public void drawForeground(Graphics g, int levelOffsetX, int levelOffsetY, Player player) {
        for (int i = 0; i < Constants.mapInfo.mapHeight; i++) {
            for (int j = 0; j < Constants.mapInfo.mapWidth; j++) {
                int l3index = currentLevel.getLayer3Index(i, j);
                g.drawImage(levelSprite[l3index], j * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetX, i * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale), null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void changeLevel() {
        game.getPlayer().keyCollected = false;
        utilz.Collisions.redrawKey(game.getPlayer());

        if(index < levels.size()) {
            currentLevel = levels.get(index);
            index++;
        } else {
            index = 0;
            currentLevel = levels.get(index);
            index++;
        }

        game.getPlayer().loadLevelData(currentLevel.getLevel1Data(), currentLevel.getLayer2Data(), currentLevel.getLayer3Data());
        game.getPlayer().getHitbox().x = currentLevel.startX;
        game.getPlayer().getHitbox().y = currentLevel.startY;
    }

    public void addLevel() {
        String fileName = JOptionPane.showInputDialog(null, "Enter filename: ");
        if(fileName == null) {
            System.out.println("Loading canceled");
            return;
        }
        if(!fileName.contains(".json")) fileName = fileName + ".json";
        Level userLevel = new Level(ParseJSON.readFromJson(fileName, 0), ParseJSON.readFromJson(fileName, 1), ParseJSON.readFromJson(fileName, 2));
        levels.add(userLevel);
        System.out.println("Success (LevelManager : 120");
    }
}
