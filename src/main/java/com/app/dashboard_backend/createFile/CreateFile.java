package com.app.dashboard_backend.createFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CreateFile {
    @Value("${filepath}")
    private String FILE_NAME;

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void createFile(){

        try{
            File file = new File(FILE_NAME);
            if(file.createNewFile()){
                System.out.println("file created at" + file.getCanonicalPath());
            } else {
                System.out.print("file already exist at " + file.getCanonicalPath());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
