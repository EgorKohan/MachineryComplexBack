package com.bsuir.controllers;

import com.bsuir.utils.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.bsuir.ApplicationConstants.IMAGES_DEFAULT_DIR;

@Validated
@RestController
@RequestMapping(IMAGES_DEFAULT_DIR)
public class ImageController {

    @GetMapping(value = "/{directory}/{filename}", consumes = "*/*", produces = {"image/*"})
    public byte[] sendPhoto(
            @NotBlank(message = "Directory must be not blank") @PathVariable("directory") String directory,
            @NotBlank(message = "Filename must be not blank") @PathVariable("filename") String filename
    ) throws IOException {
        String type = FileUploadUtil.checkAndGetImageType(filename);
        Path path = Paths.get(IMAGES_DEFAULT_DIR + File.separator + directory + File.separator + filename);
        Path absolutePath = path.toAbsolutePath();
        if (Files.exists(absolutePath)) {
            File file = new File(absolutePath.toString());
            BufferedImage image = ImageIO.read(new FileInputStream(file));
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(image, type, bao);
            return bao.toByteArray();

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File " + filename + " not found");
        }
    }

}
