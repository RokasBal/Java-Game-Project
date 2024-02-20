package Editor;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import main.Game;
import utilz.Constants;
import utilz.LoadImages;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EditorPanel extends JPanel {
    private BufferedImage[] levelSprite;
    private MouseInputs mouseInputs;
    public int selectedTile;
    public EditorPanel() {
        mouseInputs = new MouseInputs(this);
        setPanelSize();
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        loadTileMap();
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1600, 1312);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    public void render(Graphics g) {
        int index = 0;

        //Draw top background
        for(int i = 0; i < 13; i++) {
            for(int j = 0; j < 50; j++) {
                if(j % 2 == 0) {
                    if(i % 2 == 0) {
                        g.drawImage(levelSprite[42], j * 32, i * 32, null);
                    } else g.drawImage(levelSprite[43], j * 32, i * 32, null);
                } else if(i % 2 == 0) {
                    g.drawImage(levelSprite[43], j * 32, i * 32, null);
                } else g.drawImage(levelSprite[42], j * 32, i * 32, null);
            }
        }

        //Draw map background
        for(int i = 13; i < 53; i++) {
            for(int j = 0; j < 50; j++) {
                g.drawImage(levelSprite[14], j * 32, i * 32, null);
            }
        }

        //Draw grid
        for(int i = 13; i < 53; i++) {
            g.setColor(Color.gray);
            g.drawLine(0, i * 32, 32 * 51, i * 32);
        }
        for(int i = 0; i < 50; i++) {
            g.setColor(Color.gray);
            g.drawLine(i * 32, 13 * 32, i * 32, 32 * 41);
        }

        //Draw tilemap
        for(int i = 0; i < 13 ; i++) {
            for(int j = 0; j < 13; j++) {
                g.drawImage(levelSprite[index], j * 32, i * 32, null);
                index++;
            }
        }

        //Draw selected tile
        g.drawImage(levelSprite[selectedTile], 512, 80, 256, 256,null);
    }

    public void tileSelected(int x, int y) {
        selectedTile = (y / 32 * 13 + 1) + (x / 32) - 1;
    }

    private void loadTileMap() {
        BufferedImage img = LoadImages.GetSpriteImage(LoadImages.TILESET_FILE);
        levelSprite = new BufferedImage[(img.getWidth() * img.getHeight()) / Constants.mapInfo.tileSize];
        for(int j = 0; j < (img.getHeight() / Constants.mapInfo.tileSize); j++) {
            for(int i = 0; i < (img.getWidth() / Constants.mapInfo.tileSize); i++) {
                int index = j * (img.getWidth() / Constants.mapInfo.tileSize) + i;
                levelSprite[index] = img.getSubimage(i * Constants.mapInfo.tileSize, j * Constants.mapInfo.tileSize, Constants.mapInfo.tileSize, Constants.mapInfo.tileSize);
            }
        }
    }
}

