package com.metro.content.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadUserAvatar(MultipartFile file);
    String uploadStationImage(MultipartFile file);
    String uploadContentImage(MultipartFile file);
}
