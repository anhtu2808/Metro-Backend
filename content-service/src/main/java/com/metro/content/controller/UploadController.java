package com.metro.content.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.content.service.S3Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadController {

    S3Service s3Service;

    @PostMapping("/users")
    public ApiResponse<String> uploadUserAvatar(@RequestPart("file") MultipartFile file) {
        String url = s3Service.uploadUserAvatar(file);
        return ApiResponse.<String>builder().result(url).build();
    }

    @PostMapping("/stations")
    public ApiResponse<String> uploadStationImage(@RequestPart("file") MultipartFile file) {
        String url = s3Service.uploadStationImage(file);
        return ApiResponse.<String>builder().result(url).build();
    }

    @PostMapping("/contents")
    public ApiResponse<String> uploadContentImage(@RequestPart("file") MultipartFile file) {
        String url = s3Service.uploadContentImage(file);
        return ApiResponse.<String>builder().result(url).build();
    }
}
