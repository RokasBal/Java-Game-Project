package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

public class LoadImages {

    public static final String TILESET_FILE = "dungeon_set.png";
    public static final String SPRITE_FILE = "Knight_Sprite.png";
    public static final String BACKGROUND_IMAGE = "Brown.png";

    public static BufferedImage GetSpriteImage(String fileName) {
        InputStream is = LoadImages.class.getResourceAsStream("/res/" + fileName);
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
