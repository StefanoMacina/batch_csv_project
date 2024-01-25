package com.app.dashboard_backend.writeFile;

import com.app.dashboard_backend.createFile.CreateFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Component
public class WriteFile {
    @Autowired
    CreateFile cf;

    public void writeFile(MultipartFile file){
        try {
            RandomAccessFile writer = new RandomAccessFile(cf.getFILE_NAME(), "rw");
            FileChannel channel = writer.getChannel();
            ByteBuffer buff = ByteBuffer.wrap(file.getBytes());
            channel.write(buff);
            channel.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
