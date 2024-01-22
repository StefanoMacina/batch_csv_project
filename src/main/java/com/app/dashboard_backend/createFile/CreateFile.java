package com.app.dashboard_backend.createFile;

import java.io.File;
import java.io.IOException;

public class CreateFile {

    public static void createFile(){

        try{
            File newCsv = new File("src/main/resources/csv/Filename.csv");
                if(newCsv.createNewFile()){
                    System.out.print("File " + newCsv.getName() + " created.");
                } else {
                    System.out.print("File already exists.");
                }

        }catch(IOException e){
            System.out.print("Error occured during file creation");
            e.printStackTrace();

        }
    }
}
