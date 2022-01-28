package com.tzxx.common.tencentservice.bill.util;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author zhangliang
 * @date 2021/2/3.
 */
@Slf4j
public class OcrImageUtil {
    private static final int EXPENSES_IMG_LIMIT = 2400;
    private OcrImageUtil(){}

    /**
     * @param originalImagePath 原始图片路径
     * @param thumbnailImagePath 压缩后路径
     * @return true 需要压缩且成功压缩  false 不需要压缩
     */
    public static boolean thumbnail(String originalImagePath,String thumbnailImagePath){
        BufferedImage srcImg;
        try {
            srcImg = ImageIO.read(new File(originalImagePath));
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            if(srcImgWidth > EXPENSES_IMG_LIMIT || srcImgHeight > EXPENSES_IMG_LIMIT){
                int width = EXPENSES_IMG_LIMIT;
                int height = EXPENSES_IMG_LIMIT;
                if(srcImgWidth > srcImgHeight){
                    width = new BigDecimal(EXPENSES_IMG_LIMIT).multiply(new BigDecimal(srcImgWidth)
                            .divide(new BigDecimal(srcImgHeight),2, BigDecimal.ROUND_HALF_UP)).intValue();
                }else {
                    height = new BigDecimal(EXPENSES_IMG_LIMIT).multiply(new BigDecimal(srcImgHeight)
                            .divide(new BigDecimal(srcImgWidth),2, BigDecimal.ROUND_HALF_UP)).intValue();
                }
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                image.getGraphics().drawImage(srcImg, 0, 0, width, height, null);
                File  destFile = new File(thumbnailImagePath);
                ImageIO.write(image, Objects.requireNonNull(thumbnailImagePath).substring(thumbnailImagePath.lastIndexOf('.') + 1), destFile);
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            log.error("ocr image thumbnail error : {0}",e);
        }
        return false;
    }


    public static String getImageBase64(String imagePath){
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try(InputStream in = new FileInputStream(imagePath)){
            byte[] data = new byte[in.available()];
            int read = in.read(data);
            return read>0?encoder.encode(data):null;
        }catch (Exception e){
            log.error("getImageBase64 error {},imagePath is {}",e,imagePath);
        }
        return null;
    }
}
