package main;

public class Level {
    private int[][] layer1Data;
    private int[][] layer2Data;
    public int mapHeight, mapWidth;
    public int startX, startY;
    public Level(int[][] layer1Data, int[][] layer2Data, int startX, int startY) {
        this.layer1Data = layer1Data;
        this.layer2Data = layer2Data;
        this.startX = startX;
        this.startY = startY;
    }

    public int getLayer1Index(int x, int y) {
        return layer1Data[x][y];
    }

    public int getLayer2Index(int x, int y) {
        return layer2Data[x][y];
    }

    public int[][] getLevelData() {
        return layer2Data;
    }

    public int[][] getLayer1Data() {
        return layer1Data;
    }
}
