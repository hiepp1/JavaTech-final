package com.posweb.website.Service;

import java.util.Base64;

public class ImageUtils {
    public static String encodeImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
