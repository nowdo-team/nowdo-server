package com.hsjh.nowdo.common.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;


public class FileStorageUtil {
    
    private static final String UPLOAD_DIR = "uploads/profile";

    //파일 저장
    public static String saveProfileImage(MultipartFile file) throws IOException{
        if (file == null || file.isEmpty()) return null;

        File dir = new File(UPLOAD_DIR);
        if(!dir.exists()){
            dir.mkdirs();
        }

        String originalName = file.getOriginalFilename();
        if(originalName == null || !originalName.contains(".")){
            throw new IllegalArgumentException("잘못된 파일 형식입니다.");
        }
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String savedName = UUID.randomUUID() + ext;

        File saveFile = new File(UPLOAD_DIR + savedName);
        file.transferTo(saveFile);

        return savedName;
    }

    public static void deleteProfileImage(String fileName){
        File file = new File(UPLOAD_DIR + fileName);
        if (file.exists()){
            file.delete();
        }
    }

//     public ResponseEntity<String> uploadProfileImage(
//         HttpServletRequest request,
//         @RequestParam MultipartFile image
//     )   throws IOException{
//         Long userId = getUserIdFromSession(request);

//         String savedFileName = FileStorageUtil.saveProfileImage(image);

//         UserService.updateProfileImage(userId, savedFileName);

//         return ResponseEntity.ok(savedFileName);
//     }
}
