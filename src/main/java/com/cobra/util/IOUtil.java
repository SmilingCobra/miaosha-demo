package com.cobra.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class IOUtil {

    public static void write(String path,String content,boolean append){

        File file = new File(path);
        if(!file.exists()){
            File parent = file.getParentFile();
            try {
                parent.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileUtils.writeStringToFile(file,content,append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
