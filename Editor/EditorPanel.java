package Editor;

import Inputs.EditorInputs;
import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import main.Game;
import main.ParseJSON;
import utilz.Constants;
import utilz.LoadImages;

import org.json.simple.*;

import javax.swing.*;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EditorPanel extends JPanel {
    private BufferedImage[] levelSprite;
    private MouseInputs mouseInputs;
    public int selectedTile;
    public boolean drawTile = false;
    private int tileX, tileY;
    int[][] mapArray = new int[40][50];
    int[] saveArray = new int[2000];
    public EditorPanel() {
        mouseInputs = new MouseInputs(this);
        setPanelSize();
        addKeyListener(new EditorInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        loadTileMap();
        initializeMapArray();
        loadData();
        setFocusable(true);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1600, 1312);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    private void initializeMapArray() {
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 50; j++) {
                mapArray[i][j] = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int index = 0;

        //Draw top background
        for(int i = 0; i < 13; i++) {
            for(int j = 0; j < 50; j++) {
                if(j % 2 == 0) {
                    if(i % 2 == 0) {
                        g.drawImage(levelSprite[39], j * 32, i * 32, null);
                    } else g.drawImage(levelSprite[52], j * 32, i * 32, null);
                } else if(i % 2 == 0) {
                    g.drawImage(levelSprite[52], j * 32, i * 32, null);
                } else g.drawImage(levelSprite[39], j * 32, i * 32, null);
            }
        }

        //Draw map background
        for(int i = 13; i < 53; i++) {
            for(int j = 0; j < 50; j++) {
                g.drawImage(levelSprite[54], j * 32, i * 32, null);
            }
        }

        //Draw tilemap
        for(int i = 0; i < 13 ; i++) {
            for(int j = 0; j < 13; j++) {
                g.drawImage(levelSprite[index], j * 32, i * 32, null);
                index++;
            }
        }

        //Draw tilemap grid
        for(int i = 0; i < 13; i++) {
            g.setColor(Color.gray);
            g.drawLine(0, i * 32, 13 * 32, i * 32);
        }
        for(int i = 0; i < 14; i++) {
            g.setColor(Color.gray);
            g.drawLine(i * 32, 0, i * 32, 13 * 32);
        }
        render(g);
    }

    private void loadData() {
        mapArray = ParseJSON.readFromJson("test_export.json", 0);
    }


    public void render(Graphics g) {
        //Draw selected tile
        g.drawImage(levelSprite[selectedTile], 512, 80, 256, 256,null);
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 50; j++) {
                g.drawImage(levelSprite[mapArray[i][j]], j * 32, i * 32 + 416, 32, 32, null);
            }
        }

        //Draw map grid
        for(int i = 13; i < 53; i++) {
            g.setColor(Color.gray);
            g.drawLine(0, i * 32, 32 * 51, i * 32);
        }
        for(int i = 0; i < 50; i++) {
            g.setColor(Color.gray);
            g.drawLine(i * 32, 13 * 32, i * 32, 32 * 41);
        }
    }

    public void tileSelected(int x, int y) {
        selectedTile = (y / 32 * 13) + (x / 32);
        System.out.println(selectedTile);
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

    public void drawTile(int x, int y) {
        tileX = x / 32;
        tileY = (y - 416) / 32;
        mapArray[tileY][tileX] = selectedTile;
    }

    public void saveToJSON() throws IOException {
        int index = 0;
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 50; j++) {
                saveArray[index] = mapArray[i][j];
                index++;
            }
        }

        JSONArray layer1Data = new JSONArray();
        for (int value : saveArray) {
            layer1Data.add(value);
        }
        JSONArray layer2Data = new JSONArray();
        for (int value : saveArray) {
            layer2Data.add(value);
        }

        JSONObject layer1 = new JSONObject();
        layer1.put("data", layer1Data);

        JSONObject layer2 = new JSONObject();
        layer2.put("data", layer2Data);

        JSONArray layersArray = new JSONArray();
        layersArray.add(layer1);
        layersArray.add(layer2);

        JSONObject saveToFile = new JSONObject();
        saveToFile.put("layers", layersArray);

        File outputFile = new File("levels/test_export.json");
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        try(FileWriter file = new FileWriter("levels/test_export.json")) {
            file.write(saveToFile.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Save complete");
    }
}

