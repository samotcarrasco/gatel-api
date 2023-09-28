package es.mdef.apigatel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ReductorImagen {

    public static String reducirImagen(String base64Image, int newWidth, int newHeight) throws IOException {
    	
    	String base64ImageWithoutHeader = base64Image.replaceFirst("data:image/jpeg;base64,", "");

    	byte[] imageBytes = Base64.getDecoder().decode(base64ImageWithoutHeader);

        //byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage originalImage = ImageIO.read(byteArrayInputStream);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "JPEG", byteArrayOutputStream);
        byte[] resizedImageBytes = byteArrayOutputStream.toByteArray();
        String resizedBase64Image = Base64.getEncoder().encodeToString(resizedImageBytes);
        
        String finalBase64Image = "data:image/jpeg;base64," + resizedBase64Image;
        
        return finalBase64Image;
    }

}