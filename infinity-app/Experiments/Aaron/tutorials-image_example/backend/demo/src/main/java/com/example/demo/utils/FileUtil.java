package com.example.demo.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    /**
     * util function to store image file on local filesystem
     *
     * @param base64file    image file uploaded in base64 format
     * @param fPath         where to store the image file
     * @param fName         file name
     * @throws IOException
     */
    public static void saveFile(String base64file, String fPath, String fName) throws IOException {

        // parse base64 and convert it to an image
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64file);
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));

        // create directory if not exist
        Files.createDirectories(Paths.get(fPath));

        // create the image file
        try {
            File file = new File(fPath+fName);

            if (file.createNewFile()){
                ImageIO.write(img, "jpg", file);
            }else{
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load image file from filesystem
     *
     * @param filePath      absolute path to the file
     * @return              image as a byte array
     * @throws IOException
     */
    public static byte[] loadFile(String filePath) throws IOException{
        File file = new File(filePath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(file), "jpg", stream);
        byte[] image = stream.toByteArray();
        return image;
    }
}
