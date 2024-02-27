package entities;

import main.GamePanel;
import main.LevelManager;
import utilz.Constants;
import utilz.LoadImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Collisions.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerConstants.getSpriteAmount;
import static utilz.Constants.physicsControl.playerSpeed;

/**
 * Žaidėjo klasė.
 * @author Rokas Baliutavičius, 5 grupė
 */

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 13;
    public int playerAction = IDLE, playerDirection = -1;
    private boolean left, right, up, down, jump, moving = false;
    private boolean inAir = false;
    public static int startX = Constants.PlayerInfo.START_X, startY = Constants.PlayerInfo.START_Y;
    private float airSpeed = 0f;
    private GamePanel gamePanel;
    public boolean hasDied = false, keyCollected = false;
    private int animationsArraySizeX = 11, animationsArraySizeY = 8;
    public int[][] layer1Data, levelData, layer3Data;
    public Player(float x, float y, int width, int height, GamePanel gamePanel) {
        super(x, y, width, height);
        this.gamePanel = gamePanel;
        loadAnimations();
        initializeHitbox(x, y, 12f * Constants.mapInfo.gameScale, 26f * Constants.mapInfo.gameScale);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
        checkForDeath();
    }

    public void render(Graphics g, int levelOffsetX, int levelOffsetY) {
        g.drawImage(animations[animationIndex][playerAction], (int)(hitbox.x - 50 * Constants.mapInfo.gameScale) - levelOffsetX, (int)(hitbox.y - 52 * Constants.mapInfo.gameScale) - levelOffsetY, (int)(Constants.PlayerInfo.SPRITE_WIDTH * Constants.mapInfo.gameScale), (int)(Constants.PlayerInfo.SPRITE_HEIGHT * Constants.mapInfo.gameScale), null);
//        drawHitbox(g, levelOffsetX, levelOffsetY);
    }

    private void checkForDeath() {
        if(hasDied) {
            hitbox.x = gamePanel.getGame().getLevelManager().getCurrentLevel().startX;
            hitbox.y = gamePanel.getGame().getLevelManager().getCurrentLevel().startY;
            hasDied = false;
        }
    }
    private void loadAnimations() {
        InputStream is = LoadImages.class.getResourceAsStream("/res/Knight_Sprite.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
            animations = new BufferedImage[animationsArraySizeX][animationsArraySizeY];
            for(int j = 0; j < animations.length; j++) {
                for(int k = 0; k < animations[j].length; k++) {
                    animations[j][k] = img.getSubimage(j * Constants.PlayerInfo.SPRITE_WIDTH, k * Constants.PlayerInfo.SPRITE_HEIGHT, Constants.PlayerInfo.SPRITE_WIDTH, Constants.PlayerInfo.SPRITE_HEIGHT);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setAnimation(){
        int startingAnimation = playerAction;

        if(moving) {
            if(right) playerAction = RUNNING_RIGHT;
            if(left) playerAction = RUNNING_LEFT;
        } else playerAction = IDLE;

        if(inAir) {
            if(airSpeed < 0 && right) playerAction = JUMPING_RIGHT;
            else if(airSpeed < 0 && left) playerAction = JUMPING_LEFT;
            else if(airSpeed < 0) playerAction = JUMPING_RIGHT;
            else {
                if(right) playerAction = FALLING_RIGHT;
                else if(left) playerAction = FALLING_LEFT;
                else playerAction = FALLING_RIGHT;
            }
        }

        if(startingAnimation != playerAction) resetAnimationTick();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    public void loadLevelData(int[][] layer1Data, int[][] layer2Data, int[][] layer3Data) {
        this.layer1Data = layer1Data;
        this.levelData = layer2Data;
        this.layer3Data = layer3Data;
        if(!IsEntityOnGround(hitbox, layer1Data, levelData, layer2Data, this)){
            inAir = true;
        }
    }

    public int[][] returnBackgroundData() {
        return layer1Data;
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    private void updatePosition() {
        moving = false;
        if(jump)
            jump();
        if(!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if(left)
            xSpeed -= playerSpeed;
        if(right)
            xSpeed += playerSpeed;

        if(!inAir) {
            if(!IsEntityOnGround(hitbox, layer1Data, levelData, layer3Data, this)) {
                inAir = true;
            }
        }

        if(inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, gamePanel.getGame().getLevelManager().getCurrentLevel().getLevel1Data(), levelData, layer3Data, this)) {
                hitbox.y += airSpeed;
                airSpeed += Constants.physicsControl.gravity;
                updateXPosition(xSpeed);
            } else {
                hitbox.y = GetEntityYPositionByGround(hitbox, airSpeed);
                if(airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = Constants.physicsControl.fallSpeedAfterCollision;
                }
                updateXPosition(xSpeed);
            }
        } else {
            updateXPosition(xSpeed);
        }

        moving = true;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = Constants.physicsControl.jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPosition(float xSpeed) {
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, (int) hitbox.width, (int) hitbox.height, gamePanel.getGame().getLevelManager().getCurrentLevel().getLevel1Data(), levelData, layer3Data, this)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPositionByWall(hitbox, xSpeed);
        }
    }

    public void resetDirectionBool() {
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
