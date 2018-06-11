package com.kedian.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author happy
 * @version 0
 * @Package com.xjp.utils
 * @date 2018/5/8 19:12
 * @Description:工具类,对图片进行Base64解码或者加密操作,支持本地及网络
 */
public class Base64Img {
    /***
     * 接收网络地址图片,转化为base64
     * @param imgURL
     * @return
     */
    public static  String GetImageStrFromUrl(String imgURL) {
        byte[] data = null;
        try{
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
           data = readInputStream(inStream);
            inStream.read(data);
            inStream.close();
        }catch (Exception e){
//            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /***
     * 辅助工具,防止图片出错
     * @param inStream
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public static boolean GenerateImage(String imgStr, String path) {
        if (imgStr == null){
            return  false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i =0;i<b.length;i++){
                if (b[i]<0){
                    b[i]+=256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /***
     * 接收本地图片,转化为base64
     * @param imgFile
     * @return
     */
    public static String GetImgStr(String imgFile){
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static void main(String[] args) {
        String strImg = GetImageStrFromUrl("https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/s%3D220/sign=0bf1f4ccd7ca7bcb797bc02d8e086b3f/5882b2b7d0a20cf4485233d076094b36adaf99fb.jpg");
        System.out.println(strImg);
        String strImg1 = GetImgStr("F:/1.jpg");
        System.out.println("---------------");
        System.out.println(strImg1);
        System.out.println(strImg.equalsIgnoreCase(strImg));
        GenerateImage(strImg1, "F:/86619-107.jpg");
    }
}
