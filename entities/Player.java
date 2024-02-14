package entities;

import utilz.Constants;
import utilz.LoadImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerConstants.getSpriteAmount;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 30;
    private int playerAction = RUNNING_RIGHT, playerDirection = -1;
    private boolean left, right, up, down, moving = false;
    public static int startX = Constants.PlayerInfo.START_X, startY = Constants.PlayerInfo.START_Y;
    private float playerSpeed = 2.5f;
    private int animationsArraySizeX = 9, animationsArraySizeY = 3, spriteSizeX = 48, spriteSizeY = 48;
    private int[][] levelData;
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateHitbox();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[animationIndex][playerAction], (int)x, (int)y, 96, 96, null);
        drawHitbox(g);
    }

    private void loadAnimations() {
        InputStream is = LoadImages.class.getResourceAsStream("/res/mushroomSprite.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
            animations = new BufferedImage[animationsArraySizeX][animationsArraySizeY];
            for(int j = 0; j < animations.length; j++) {
                for(int k = 0; k < animations[j].length; k++) {
                    animations[j][k] = img.getSubimage(j * spriteSizeX, k * spriteSizeY, spriteSizeX, spriteSizeY);
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
        if(moving) {
            if(up || down) playerAction = RUNNING_RIGHT;
            if(right) playerAction = RUNNING_RIGHT;
            if(left) playerAction = RUNNING_LEFT;
        } else playerAction = IDLE;
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
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
        if(left && !right) {
            if(x < 0 || x > 1500) {
                x = startX;
                y = startY;
            }
            x -= playerSpeed;
            moving = true;
        } else if (right && !left ) {
            x += playerSpeed;
            if(x < 0 || x > 1500) {
                x = startX;
                y = startY;
            }
            moving = true;
        }

        if(up && !down) {
            y -= playerSpeed;
            if(y >= 800 || y < 0) {
                x = startX;
                y = startY;
            }
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            if(y >= 800 || y < 0) {
                x = startX;
                y = startY;
            }
            moving = true;
        }

        if(!up && !down && !left && !right) {
            moving = false;
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
}
