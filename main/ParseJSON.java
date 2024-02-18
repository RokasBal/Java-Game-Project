package main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utilz.Constants;

import java.io.*;

public class ParseJSON {
    public ParseJSON() {
        //readFromJson();
    }

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
        //Lukšo darbas

        int[][] mapData = new int[Constants.mapInfo.mapHeight][Constants.mapInfo.mapWidth];
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
            JSONObject layer1 = (JSONObject) array.get(selectionIndex);
            JSONArray layerData = (JSONArray) layer1.get("data");

            int index = 0;
            for(int i = 0; i < Constants.mapInfo.mapHeight; i++) {
                for(int j = 0; j < Constants.mapInfo.mapWidth; j++) {
                    mapData[i][j] = ((Long) layerData.get(index)).intValue();
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
