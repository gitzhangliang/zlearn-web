package com.zl;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author yudian-it
 */
public class DownloadUtil {

    public static Object downLoad(String urlAddress, String outPath, String outFileName) {
        URL url;
        try {
            url = new URL(urlAddress);
        } catch (MalformedURLException e) {
            throw new RuntimeException("创建URL出错",e);
        }
        String realPath = outPath + outFileName;
        File dirFile = new File(outPath);
        if (!dirFile.exists()) {
            boolean mkdirs = dirFile.mkdirs();
            if(!mkdirs){
                throw new RuntimeException("创建文件夹出错");
            }
        }
        URLConnection connection;
        try {
            connection = url.openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("URLConnection出错",e);
        }
        try (InputStream in = connection.getInputStream();
             FileOutputStream os = new FileOutputStream(realPath)){
            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = in.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
            return realPath;
        } catch (IOException e) {
            throw new RuntimeException("文件下载出错",e);
        }
    }

    public static void main(String[] args) {
        try{
            a();
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }
    public static void a(){
        try{
//            File file = new File("");
//            InputStream inputStream = new FileInputStream(file);
            int i = 1/0;
        }catch (Exception e){
            throw e;
        }
    }
}
