package hei.school.sarisary.service;

import hei.school.sarisary.exception.BadRequestException;
import hei.school.sarisary.file.BucketComponent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageProcessingService {
    private final BucketComponent bucketComponent;
    private static final String BUCKET_KEY = "images/";
    private static final String IMAGE_EXTENSION = ".png";
    private static final String ORIGINAL_SUFFIX = "_original";
    private static final String CONVERTED_SUFFIX = "_converted";

    private File uploadImage(String fileName, BufferedImage image) throws IOException {
        String fullFileName = fileName + IMAGE_EXTENSION;
        File tempFile = File.createTempFile(fileName, IMAGE_EXTENSION);
        ImageIO.write(image, "png", tempFile);
        bucketComponent.upload(tempFile, BUCKET_KEY + fullFileName);
        return tempFile;
    }

    public void convertToBlackAndWhite(byte[] inputImage, String fileName) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(inputImage));
            if (image == null) {
                throw new BadRequestException("Please make sure it is a valid image");
            }

            // save the original image
            uploadImage(fileName + ORIGINAL_SUFFIX, image);

            BufferedImage convertedImage =
                    new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            convertedImage.getGraphics().drawImage(image, 0, 0, null);

            // save the converted image
            uploadImage(fileName + CONVERTED_SUFFIX, image);
        } catch (IOException e) {
            throw new BadRequestException("Please make sure it is a valid image");
        }
    }
}