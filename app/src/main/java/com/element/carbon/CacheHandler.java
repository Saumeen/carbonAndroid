package com.element.carbon;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CacheHandler {

    public static void saveFile(String fileName, File cacheDir, Object data) throws IOException {
        File file = new File(cacheDir, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeObject(data);
        fos.close();
        objectOutputStream.close();
    }

    public static Object getFile(String fileName, File cacheDir) throws IOException,ClassNotFoundException {
        Object ob = new Object();
        File file = new File(cacheDir, fileName);
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fin);
        ob = objectInputStream.readObject();
        objectInputStream.close();
        fin.close();
        return ob;
    }
}
