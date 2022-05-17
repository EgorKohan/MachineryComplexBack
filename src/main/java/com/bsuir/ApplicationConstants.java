package com.bsuir;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstants {

    public static final String PATH_TO_MODEL = "model.txt";
    public static final String SELF_PROPELLED_MACHINES_UPLOAD_DIR = "/self-propelled-machine-images";
    public static final String TRAILERS_UPLOAD_DIR = "/trailer-images";
    public static final String IMAGE_TYPE_REGEXP = "png|jpeg|jpg";
    public static final String INVALID_IMAGE_TYPE_ERROR_MESSAGE = "Not supported type of media. Supported: png, jpeg, jpg.";
    public static final String IMAGES_DEFAULT_DIR = "/images";

}
