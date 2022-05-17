package com.bsuir.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static com.bsuir.ApplicationConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUploadUtil {

    public static String checkAndGetImageType(String filename) {
        String[] nameAndType = filename.split("\\.");
        String type = nameAndType[1];
        if (!type.matches(IMAGE_TYPE_REGEXP))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_IMAGE_TYPE_ERROR_MESSAGE);
        return type;
    }

    public static String saveFile(String uploadDir, MultipartFile file) {
        if (file == null) return null;
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file!");

        checkAndGetImageType(originalFilename);

        String uniqueFilename = UUID.randomUUID() + originalFilename;
        Path uploadPath = Paths.get(IMAGES_DEFAULT_DIR + uploadDir);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(uniqueFilename);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while saving file", e);
        }
        return IMAGES_DEFAULT_DIR + uploadDir + "/" + uniqueFilename;
    }

}
