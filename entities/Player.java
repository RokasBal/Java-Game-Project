package entities;

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

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 30;
    private int playerAction = RUNNING_RIGHT, playerDirection = -1;
    private boolean left, right, up, down, jump, moving = false;
    private boolean inAir = false;
    public static int startX = Constants.PlayerInfo.START_X, startY = Constants.PlayerInfo.START_Y;
    private float playerSpeed = 2.5f, airSpeed = 0f;
    private int animationsArraySizeX = 9, animationsArraySizeY = 3, spriteSizeX = 48, spriteSizeY = 48;
    private int[][] levelData;
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initializeHitbox(x, y, 23f, 35f);
    }

    public void update() {
        updatePosition();
        //updateHitbox();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[animationIndex][playerAction], (int)(hitbox.x - 12), (int)(hitbox.y - 4), 48, 48, null);
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

        if(inAir) {
//            if(airSpeed < 0) playerAction = JUMP;
//            if(airSpeed < 0 && right) playerAction = JUMP_RIGHT;
//            if(airSpeed <0 && left) playerAction = JUMP_LEFT;
//            else playerAction = FALLING;
        }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if(!IsEntityOnGround(hitbox, levelData)){
            inAir = true;
        }
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
            if(!IsEntityOnGround(hitbox, levelData)) {
                inAir = true;
            }
        }

        if(inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
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
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, (int) hitbox.width, (int) hitbox.height, levelData)) {
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
