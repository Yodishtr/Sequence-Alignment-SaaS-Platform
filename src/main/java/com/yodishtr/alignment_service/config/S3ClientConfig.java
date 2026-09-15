package com.yodishtr.alignment_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3ClientConfig {

  private final S3ConfigProp s3ConfigProp;

  public S3ClientConfig(S3ConfigProp configProp) {
    this.s3ConfigProp = configProp;
  }

  @Bean
  public S3Client createS3Client() {
    return S3Client.builder()
        .region(Region.of(s3ConfigProp.getRegion()))
        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
            s3ConfigProp.getAccessKey(), s3ConfigProp.getSecretKey())))
        .build();
  }
}
