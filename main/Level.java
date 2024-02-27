package main;

public class Level {
    private int[][] layer1Data, layer2Data, layer3Data;
    public int mapHeight, mapWidth;
    public int startX, startY;
    public Level(int[][] layer1Data, int[][] layer2Data, int[][] layer3Data) {
        this.layer1Data = layer1Data;
        this.layer2Data = layer2Data;
        this.layer3Data = layer3Data;
        getStartPosition();
    }

    public void getStartPosition() {
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 50; j++) {
                if(layer3Data[i][j] == 168 ) {
                    startX = j * 48;
                    startY = i * 48 - 1;
                    layer3Data[i][j] = 0;
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

    public int getLayer3Index(int x, int y) {
        return layer3Data[x][y];
    }


    public int[][] getLevel1Data() {
        return layer1Data;
    }

    public int[][] getLayer2Data() {
        return layer2Data;
    }

    public int[][] getLayer3Data() {
        return layer3Data;
    }

}
