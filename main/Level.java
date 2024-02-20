package main;

public class Level {
    private int[][] layer1Data;
    private int[][] layer2Data;
    public int mapHeight, mapWidth;
    public int startX, startY;
    public Level(int[][] layer1Data, int[][] layer2Data) {
        this.layer1Data = layer1Data;
        this.layer2Data = layer2Data;
        getStartPosition();
    }

    public void getStartPosition() {
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 50; j++) {
                if(layer1Data[i][j] == 168) {
                    startX = j * 48;
                    startY = i * 48;
                }
            }
        }
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 50; j++) {
                if(layer2Data[i][j] == 168) {
                    startX = j * 48;
                    startY = i * 48;
                }
            }
        }
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
