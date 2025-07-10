package com.metro.content.service.impl;

import com.metro.content.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client userAvatarClient;
    private final S3Client stationImageClient;
    private final S3Client contentImageClient;

    private final String userAvatarBucket;
    private final String stationImageBucket;
    private final String contentImageBucket;

    @Autowired
    public S3ServiceImpl(
            @Qualifier("userAvatarS3Client") S3Client userAvatarClient,
            @Qualifier("stationImageS3Client") S3Client stationImageClient,
            @Qualifier("contentImageS3Client") S3Client contentImageClient,
            @Value("${aws.s3.buckets.user-avatar}") String userAvatarBucket,
            @Value("${aws.s3.buckets.station-image}") String stationImageBucket,
            @Value("${aws.s3.buckets.content-image}") String contentImageBucket
    ) {
        this.userAvatarClient = userAvatarClient;
        this.stationImageClient = stationImageClient;
        this.contentImageClient = contentImageClient;
        this.userAvatarBucket = userAvatarBucket;
        this.stationImageBucket = stationImageBucket;
        this.contentImageBucket = contentImageBucket;
    }

    @Override
    public String uploadUserAvatar(MultipartFile file) {
        return upload(file, userAvatarBucket, userAvatarClient);
    }

    @Override
    public String uploadStationImage(MultipartFile file) {
        return upload(file, stationImageBucket, stationImageClient);
    }

    @Override
    public String uploadContentImage(MultipartFile file) {
        return upload(file, contentImageBucket, contentImageClient);
    }

    private String upload(MultipartFile file, String bucket, S3Client client) {
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .build();
        try {
            client.putObject(putReq, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
        GetUrlRequest urlReq = GetUrlRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        return client.utilities().getUrl(urlReq).toExternalForm();
    }
}
