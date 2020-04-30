package com.zl.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zhangliang
 * @date 2020/3/9.
 */
@Slf4j
public class ZipUtil {
    private ZipUtil(){}

    /**
     * 文件压缩
     * @param srcFile  目录或者单个文件
     * @param destFile 压缩后的ZIP文件
     */
    public static void compress(File srcFile, File destFile){
        try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destFile))){
            if(srcFile.isDirectory()){
                File[] files = srcFile.listFiles();
                if (files != null && files.length > 0) {
                    for(File file : files){
                        doCompress(file, out);
                    }
                }
            }else {
            doCompress(srcFile, out);
        }
        }catch (Exception e){
            log.error("压缩文件出错:{0}",e);
            throw new RuntimeException("压缩文件出错");
        }
    }

    private static void doCompress(File file, ZipOutputStream out){
        if( file.exists() ){
            try (FileInputStream fis = new FileInputStream(file)){
                byte[] buffer = new byte[1024];
                out.putNextEntry(new ZipEntry(file.getName()));
                int len = 0 ;
                // 读取文件的内容,打包到zip文件
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.closeEntry();
            }catch (Exception e){
                log.error("压缩文件出错:{0}",e);
                throw new RuntimeException("压缩文件出错");
            }
        }
    }
}
