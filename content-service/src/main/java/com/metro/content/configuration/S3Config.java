package com.metro.content.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws.s3.region}")
    String region;

    @Value("${aws.s3.credentials.user-avatar.access-key}")
    String userAvatarAccessKey;
    @Value("${aws.s3.credentials.user-avatar.secret-key}")
    String userAvatarSecretKey;

    @Value("${aws.s3.credentials.station-image.access-key}")
    String stationAccessKey;
    @Value("${aws.s3.credentials.station-image.secret-key}")
    String stationSecretKey;

    @Value("${aws.s3.credentials.content-image.access-key}")
    String contentAccessKey;
    @Value("${aws.s3.credentials.content-image.secret-key}")
    String contentSecretKey;

    @Bean(name = "userAvatarS3Client")
    public S3Client userAvatarS3Client() {
        return buildClient(userAvatarAccessKey, userAvatarSecretKey);
    }

    @Bean(name = "stationImageS3Client")
    public S3Client stationImageS3Client() {
        return buildClient(stationAccessKey, stationSecretKey);
    }

    @Bean(name = "contentImageS3Client")
    public S3Client contentImageS3Client() {
        return buildClient(contentAccessKey, contentSecretKey);
    }

    private S3Client buildClient(String accessKey, String secretKey) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .build();
    }
}
