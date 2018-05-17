package com.example.rs.util;

import com.example.rs.domain.Movie;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class CerTrans {
    public static Long[] getRecommend(Long userId) {
        try {
            LineNumberReader lineReader = new LineNumberReader(new FileReader("data/cer.txt"));
            String line = "";
            int count = 0;
            while ((line = lineReader.readLine()) != null) {
                count++;
                if (count % 100 == 0)
                    System.out.println(count);
                String[] a = line.split(",");
                Long id = Long.parseLong(a[0].trim());
                if (id.equals(userId)) {
                    Long[] rl = new Long[12];
                    for (int i = 1; i < a.length; i++) {
                        rl[i - 1] = Long.parseLong(a[i].trim());
                    }
                    return rl;
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Long[] a = CerTrans.getRecommend(2l);
        String s = "";
        for (int i = 0; i < 12; i++) {
            s += a[i] + ",";
        }
        System.out.println(s);
    }
}
