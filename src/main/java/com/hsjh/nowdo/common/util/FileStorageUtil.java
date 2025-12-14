package com.hsjh.nowdo.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;



public class FileStorageUtil {
    
    private static final String UPLOAD_DIR = "uploads/profile";

    //파일 저장
    public static String saveProfileImage(String imageURL) throws IOException{
        if (imageURL == null || imageURL.isBlank()) {
            throw new IllegalArgumentException("이미지 URL이 해당되지 않습니다.");
        }

        String ext = ".jpg";
        if(imageURL.contains(".")){
            ext = imageURL.substring(imageURL.lastIndexOf("."));
            if(ext.length() > 5) {  // 외부 확장자 방어
                ext = ".jpg";
            }
        }

        String savedName = UUID.randomUUID() + ext;

        File dir = new File("uploads/profile/");
        if (!dir.exists()) dir.mkdirs();

        File saveFile = new File(dir, savedName);

        try (InputStream in = new URL(imageURL).openStream()) {
            Files.copy(in, saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        }

        return savedName;
    }

    public static void deleteProfileImage(String fileName){
        File file = new File(UPLOAD_DIR + fileName);
        if (file.exists()){
            file.delete();
        }
    }
}