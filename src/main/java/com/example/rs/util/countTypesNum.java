package com.example.rs.util;

import com.example.rs.javabean.Movie;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class countTypesNum {

    public static void main(String[] args) {
        int c = 0;
        Set<String> s = new HashSet<>();
        List<String> typearray;
        try {
            LineNumberReader lineReader = new LineNumberReader(new FileReader(
                    "data/ml-latest-small/movies.csv"));
            String line = "";
            s.clear();
            while ((line = lineReader.readLine()) != null) {
                c++;
                if (c % 100 == 0)
                    System.out.println(c);
                typearray = getTypeArray(line);
                for(String str : typearray) {
                    s.add(str);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("error id num:" + Integer.toString(c));
            e.printStackTrace();
        }
        System.out.println("types count: " + s.size());
        System.out.println(s);
    }

    public static List<String> getTypeArray(String line) {
        String[] mo;
        if (line.contains("\"")) {
            mo = new String[3];
            mo[0] = line.substring(0, line.indexOf("\"") - 1);
            mo[1] = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
            mo[2] = line.substring(line.lastIndexOf("\"") + 2);
        } else {
            mo = line.split(",");
        }

        List<String> type = new ArrayList<String>();
        if (!mo[2].contains("(no genres listed)")) {
            String[] t = mo[2].split("\\|");
            for (int i = 0; i < t.length; i++) {
                type.add(t[i]);
            }
        } else {
            type.add("no genres listed");
        }
        return type;
    }
}
