package main;

import entities.Player;
import utilz.Constants;

import java.awt.*;
import java.util.Objects;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 165;
    private final int UPS_SET = 200;
    private Player player;
    private ParseJSON parseJSON;
    private LevelManager levelManager;
    private int spriteWidth = Constants.PlayerInfo.WIDTH;
    private int spriteHeight = Constants.PlayerInfo.HEIGHT;
    public int levelOffsetX, levelOffsetY;
    private int leftBorder = (int)(0.4 * Constants.mapInfo.visibleWidth);
    private int rightBorder = (int)(0.6 * Constants.mapInfo.visibleWidth);
    private int upperBorder = (int)(0.2 * Constants.mapInfo.visibleHeight);
    private int lowerBorder = (int)(0.8 * Constants.mapInfo.visibleHeight);
    private int maxTilesOffsetX = Constants.mapInfo.mapWidth - Constants.mapInfo.visibleTilesX;
    private int maxTilesOffsetY = Constants.mapInfo.mapHeight - Constants.mapInfo.visibleTilesY;
    private int maxOfsetX = maxTilesOffsetX * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
    private int maxOfsetY = maxTilesOffsetY * (int)(Constants.mapInfo.tileSize * Constants.mapInfo.gameScale);
    private String osName = System.getProperty("os.name");
    public Game() {
        initializeClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        //parseJSON.readFromJson();

        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initializeClasses() {
        levelManager = new LevelManager(this);
        player = new Player(Player.startX, Player.startY, Constants.PlayerInfo.WIDTH, Constants.PlayerInfo.HEIGHT);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
        levelManager.update();
        checkIfNearBorder();
    }

    public void render(Graphics g) {
        levelManager.draw(g, levelOffsetX, levelOffsetY);
        player.render(g, levelOffsetX, levelOffsetY);
    }
    public void checkIfNearBorder() {
        int playerX = (int)player.getHitbox().x;
        int playerY = (int)player.getHitbox().y;

        int diffX = playerX - levelOffsetX;
        int diffY = playerY - levelOffsetY;

        if(diffX > rightBorder) {
            levelOffsetX += diffX - rightBorder; }
        else if(diffX < leftBorder)
            levelOffsetX += diffX - leftBorder;

        if(diffY > lowerBorder) {
            levelOffsetY += diffY - lowerBorder;
        }
        else if(diffY < upperBorder)
            levelOffsetY += diffY - upperBorder;

        if(levelOffsetX > maxOfsetX)
            levelOffsetX = maxOfsetX;
        else if(levelOffsetX < 0)
            levelOffsetX = 0;

        if(levelOffsetY > maxOfsetY)
            levelOffsetY = maxOfsetY;
        else if(levelOffsetY < 0)
            levelOffsetY = 0;
    }
    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirectionBool();
    }

    public void run() {

        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;
        double deltaU = 0;
        double deltaF = 0;
        long lastCheck = System.currentTimeMillis();

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        while(true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1) {
                gamePanel.repaint();
                if(Objects.equals(osName, "Linux")) Toolkit.getDefaultToolkit().sync();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println(frames + " " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
