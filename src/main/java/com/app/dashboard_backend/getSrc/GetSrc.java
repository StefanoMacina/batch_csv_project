package com.app.dashboard_backend.getSrc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetSrc {

    private static String fileToRead = "src/main/resources/src";
    public static String srcPath(){

        try{
            BufferedReader readSrc = new BufferedReader(new FileReader(fileToRead));
            String src = readSrc.readLine().trim();
            readSrc.close();
            return src;
        }catch (IOException e){
            System.out.print("Unable to find src path");
            e.printStackTrace();
            return null;
        }
    }

}


//basePath = path ("order.csv")