package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    //protected int hitboxWidth = 46, hitboxHeight = 82;
    protected Rectangle2D.Float hitbox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        //initializeHitbox();
    }

    protected void drawHitbox(Graphics g) {
        g.setColor(Color.red);
        //g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void initializeHitbox(float x, float y, float hitboxWidth, float hitboxHeight) {
        hitbox = new Rectangle2D.Float(x, y, hitboxWidth, hitboxHeight);
    }

    protected void updateHitbox() {
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
