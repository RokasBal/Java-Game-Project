package main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utilz.Constants;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class ParseJSON {
    public ParseJSON() {
        readFromJson();
    }

    public static int[][] readFromJson() {
        //lukšo darbas

        int[][] mapData = new int[Constants.mapInfo.mapHeight][Constants.mapInfo.mapWidth];
        InputStream is = ParseJSON.class.getResourceAsStream("/res/testmap2.json");
        try {
            System.out.println("TEST");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder contents = new StringBuilder();

            int character;
            while((character = reader.read()) != -1) {
                contents.append((char) character);
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(contents.toString());

            long height = (long) jsonObject.get("height");
            System.out.println(height);

            JSONArray array = (JSONArray) jsonObject.get("layers");
            JSONObject layer1 = (JSONObject) array.get(0);
            JSONArray layer1data = (JSONArray) layer1.get("data");
            //System.out.println(layer1data.size());

            int index = 0;
            for(int i = 0; i < Constants.mapInfo.mapHeight; i++) {
                for(int j = 0; j < Constants.mapInfo.mapWidth; j++) {
                    mapData[i][j] = ((Long) layer1data.get(index)).intValue();
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
