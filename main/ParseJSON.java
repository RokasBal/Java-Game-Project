package main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utilz.Constants;

import java.io.*;

/**
 * Klasė, naudojama nuskaityti lygių informaciją, išsaugotą JSON formatu.
 * @author Rokas Baliutavičius, 5 grupė
 * @author Aurelijus Lukšas, 5 grupė
 */

public class ParseJSON {

    public static long[] readHeighAndWidth(String fileName) {
        long[] heightWidth = new long[2];
        InputStream is = ParseJSON.class.getResourceAsStream("/res/" + fileName);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder contents = new StringBuilder();

            int character;
            while((character = reader.read()) != -1) {
                contents.append((char) character);
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(contents.toString());
            JSONArray array = (JSONArray) jsonObject.get("layers");
            JSONObject layer1 = (JSONObject) array.getFirst();

            heightWidth[0] = (long) layer1.get("height");
            heightWidth[1] = (long) layer1.get("width");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return heightWidth;
    }

    public static int[][] readFromJson(String fileName, int selectionIndex) {

        int[][] mapData = new int[Constants.mapInfo.mapHeight][Constants.mapInfo.mapWidth];
        InputStream is = ParseJSON.class.getResourceAsStream("/levels/" + fileName);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder contents = new StringBuilder();

            int character;
            while((character = reader.read()) != -1) {
                contents.append((char) character);
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(contents.toString());

            JSONArray array = (JSONArray) jsonObject.get("layers");
            JSONObject layer1 = (JSONObject) array.get(selectionIndex);
            JSONArray layerData = (JSONArray) layer1.get("data");

            int index = 0;
            for(int i = 0; i < Constants.mapInfo.mapHeight; i++) {
                for(int j = 0; j < Constants.mapInfo.mapWidth; j++) {
                    mapData[i][j] = ((Long) layerData.get(index)).intValue();
                    if(mapData[i][j] != 0)  mapData[i][j] = mapData[i][j] - 1;
                    index++;
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mapData;
    }
}
