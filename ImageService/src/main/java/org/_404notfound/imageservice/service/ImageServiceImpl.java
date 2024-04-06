package org._404notfound.imageservice.service;

import org._404notfound.imageservice.model.Image;
import org._404notfound.imageservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {
    // Assuming images are stored in a directory named "images" within the project directory
    private static final String IMAGE_DIRECTORY = "images/"; // Update with your images directory's absolute path

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public byte[] getImageData(Long imageId) {
        Image image = imageRepository.findById(imageId).orElse(null);
        if (image != null) {
            try {
                // Construct the image file path using the image name from the entity
                Path imagePath = Paths.get(IMAGE_DIRECTORY + image.getName());
                return Files.readAllBytes(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
