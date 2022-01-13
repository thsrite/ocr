package com;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class OcrApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcrApplication.class, args);
    }

    @RestController
    public class qcr {

        @CrossOrigin("*")
        @PostMapping("qcr")
        public String qcr(@RequestPart MultipartFile file, Integer chi) throws IOException, TesseractException {
            ITesseract instance = new Tesseract();
            // 指定训练数据集合的路径
            instance.setDatapath("/usr/local/share/tessdata");
            // 指定为中文识别
//            instance.setLanguage("chi_simmm");//中文识别
//            instance.setLanguage("chi_simm");//中文识别
            instance.setLanguage("chi_sim");//中文识别
//            instance.setLanguage("eng");//中文识别

            // 指定识别图片
//            File imgDir = new File(filePath);
            long startTime = System.currentTimeMillis();

            String originalFilename = file.getOriginalFilename();
            //文件扩展名
            int index = originalFilename.lastIndexOf(".");
            String extensionName = index > 0 ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";

            String ocrResult;

            if (extensionName.equals("png") || extensionName.equals("jpg") || extensionName.equals("jpeg")) {
                BufferedImage image = ImageIO.read(file.getInputStream());

                int width = image.getWidth();
                int height = image.getHeight();

                BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY

                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        int rgb = image.getRGB(i, j);
                        grayImage.setRGB(i, j, rgb);
                    }
                }

                grayImage = ImageHelper.getScaledInstance(grayImage, grayImage.getWidth() * 5, grayImage.getHeight() * 5);

                ocrResult = instance.doOCR(grayImage);
            } else {
                File imgDir = File.createTempFile(originalFilename, extensionName);
                file.transferTo(imgDir);
                ocrResult = instance.doOCR(imgDir);
            }

            // 输出识别结果
            System.out.println("OCR Result: \n" + ocrResult + "\n 耗时：" + (System.currentTimeMillis() - startTime) + "ms");

            if (chi != null && chi == 1) {
                return ocrResult.replace(" ", "");
            } else {
                return ocrResult;
            }
        }
    }
}
