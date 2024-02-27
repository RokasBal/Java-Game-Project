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

/**
 * Pagrindinė lygiu editoriaus klasė.
 * Šioje klasėje yra visas editoriaus funkcionalumas
 * @author Rokas Baliutavičius, 5 grupė
 */

public class EditorPanel extends JPanel {
    private BufferedImage[] levelSprite;
    private MouseInputs mouseInputs;
    public int selectedTile;
    public boolean drawTile = false;
    private int tileX, tileY;
    public static int selectedLayer = 1;
    int[][] mapArray = new int[40][50], layer1 = new int[40][50], layer2 = new int[40][50], layer3 = new int[40][50];
    int[] saveArray = new int[2000], saveL1 = new int[2000], saveL2 = new int[2000], saveL3 = new int[2000];
    Font selectionFont = new Font("8Bit Wonder", Font.PLAIN, 48);
    public EditorPanel() {
        mouseInputs = new MouseInputs(this);
        setPanelSize();
        addKeyListener(new EditorInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        loadTileMap();
        initializeMapArray();
//        loadData();
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
                g.drawImage(levelSprite[57], j * 32, i * 32, null);
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

    public void loadData() {
        String fileName = JOptionPane.showInputDialog(null, "Enter filename: ");
        if(fileName == null) {
            System.out.println("Loading canceled");
            return;
        }
        if(!fileName.contains(".json")) fileName = fileName + ".json";

        layer1 = ParseJSON.readFromJson(fileName, 0);
        layer2 = ParseJSON.readFromJson(fileName, 1);
        layer3 = ParseJSON.readFromJson(fileName, 2);
//        for(int i = 0; i < 40; i++) {
//            for(int j = 0; j < 50; j++) {
//                layer1[i][j] = layer1[i][j] + 1;
//                layer2[i][j] = layer2[i][j] + 1;
//                layer3[i][j] = layer3[i][j] + 1;
//            }
//        }
    }


    public void render(Graphics g) {
        //Draw selected tile
        g.drawImage(levelSprite[selectedTile], 512, 80, 256, 256,null);
        g.setColor(Color.BLACK);
        g.setFont(selectionFont);
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 50; j++) {
                g.drawImage(levelSprite[layer1[i][j]], j * 32, i * 32 + 416, 32, 32, null);
                g.drawImage(levelSprite[layer2[i][j]], j * 32, i * 32 + 416, 32, 32, null);
                g.drawImage(levelSprite[layer3[i][j]], j * 32, i * 32 + 416, 32, 32, null);
            }
        }

        switch(selectedLayer) {
            case 0:
                g.drawString("Background", 50 * 32 - 500, 13 * 32 / 2);
                break;
            case 1:
                g.drawString("Solid", 50 * 32 - 400, 13 * 32 / 2);
                break;
            case 2:
                g.drawString("Foreground", 50 * 32 - 500, 13 * 32 / 2);
                break;
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
        if(selectedTile == 168) {
            layer3[tileY][tileX] = selectedTile;
            return;
        }
        if(selectedTile == 88 || selectedTile == 103 || selectedTile == 116) {
            layer1[tileY][tileX] = selectedTile;
            return;
        }
        switch(selectedLayer){
            case 0:
                layer1[tileY][tileX] = selectedTile;
                break;
            case 1:
                layer2[tileY][tileX] = selectedTile;
                break;
            case 2:
                layer3[tileY][tileX] = selectedTile;
                break;
        }
//        mapArray[tileY][tileX] = selectedTile;
    }

    public void saveToJSON() throws IOException {
        int index = 0;
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 50; j++) {
                saveL1[index] = layer1[i][j] + 1;
                saveL2[index] = layer2[i][j] + 1;
                saveL3[index] = layer3[i][j] + 1;
                index++;
            }
        }

        JSONArray layer1Data = new JSONArray();
        for (int value : saveL1) {
            layer1Data.add(value);
        }
        JSONArray layer2Data = new JSONArray();
        for (int value : saveL2) {
            layer2Data.add(value);
        }
        JSONArray layer3Data = new JSONArray();
        for (int value : saveL3) {
            layer3Data.add(value);
        }

        JSONObject layer1 = new JSONObject();
        layer1.put("data", layer1Data);

        JSONObject layer2 = new JSONObject();
        layer2.put("data", layer2Data);

        JSONObject layer3 = new JSONObject();
        layer3.put("data", layer3Data);

        JSONArray layersArray = new JSONArray();
        layersArray.add(layer1);
        layersArray.add(layer2);
        layersArray.add(layer3);

        JSONObject saveToFile = new JSONObject();
        saveToFile.put("layers", layersArray);

        String fileName = JOptionPane.showInputDialog(null, "Enter desired filename: ");
        if(fileName == null) {
            requestFocus();
            System.out.println("Saving canceled");
            return;
        }
        if(fileName.contains(".json")) {
            File outputFile = new File("levels/" + fileName);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            try(FileWriter file = new FileWriter("levels/" + fileName)) {
                file.write(saveToFile.toJSONString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File outputFile = new File("levels/" + fileName + ".json");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            try(FileWriter file = new FileWriter("levels/" + fileName + ".json")) {
                file.write(saveToFile.toJSONString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Save complete");
    }
}

