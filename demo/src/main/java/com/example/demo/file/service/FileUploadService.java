package com.example.demo.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {
    
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;
    
    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일은 업로드할 수 없습니다.");
        }
        
        // 업로드 디렉토리 생성
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // 파일명 중복 방지를 위해 UUID 사용
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFilename = UUID.randomUUID().toString() + fileExtension;
        
        // 파일 저장
        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // 저장된 파일 경로 반환
        return "/uploads/" + newFilename;
    }
    
    public void deleteFile(String filePath) {
        try {
            if (filePath != null && filePath.startsWith("/uploads/")) {
                String filename = filePath.substring("/uploads/".length());
                Path path = Paths.get(uploadDir).resolve(filename);
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            // 파일 삭제 실패해도 계속 진행
            System.err.println("파일 삭제 실패: " + filePath);
        }
    }
}

