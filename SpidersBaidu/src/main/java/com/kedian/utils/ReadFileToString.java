package com.kedian.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author happy
 * @version 0
 * @Package com.example.demo.utils
 * @date 2018/5/14 16:06
 * @Description:
 */
public class ReadFileToString {
    /**
     * 读取文件到字符串
     * @param fileName
     * @return
     */
    public static String readToString(String fileName) {
        String str="";
        File file = new File(fileName);
        try {
            FileInputStream in=new FileInputStream(file);
            // size  为字串的长度 ，这里一次性读完
            int size=in.available();
            byte[] buffer=new byte[size];
            in.read(buffer);
            in.close();
            str=new String(buffer,"utf-8");

        } catch (IOException e) {
            // TODO Auto-generated catch block
//            return null;
            e.printStackTrace();
        }
        return str;
    }

}
