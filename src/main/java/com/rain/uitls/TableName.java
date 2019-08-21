package com.rain.uitls;

import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TableName {

    public static List<String> getTableName() {
        List<String> list = new ArrayList<String>();
        try {
            File file = ResourceUtils.getFile("classpath:table_name.txt");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line="";

            while ((line=bufferedReader.readLine())!=null) {
                list.add(line);
            }
           return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
