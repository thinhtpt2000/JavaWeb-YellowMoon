/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ThinhTPT
 */
public class FileHelper implements Serializable {

    public static Map<String, String> getSiteMapFromTextFile(String path)
            throws FileNotFoundException, IOException {
        Map<String, String> siteMap = null;
        File f = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            f = new File(path);
            if (f.exists() && f.isFile()) {
                fr = new FileReader(f);
                br = new BufferedReader(fr);

                String line = br.readLine();
                while (line != null) {
                    String[] mapItem = line.split("=");
                    if (mapItem.length == 2) {
                        String key = mapItem[0].trim();
                        String value = mapItem[1].trim();

                        if (siteMap == null) {
                            siteMap = new HashMap<>();
                        }
                        siteMap.put(key, value);
                    }
                    line = br.readLine();
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        return siteMap;
    }
}
