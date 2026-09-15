package com.yodishtr.alignment_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
public class S3ConfigProp {
  private String region;
  private String bucketName;
  private String accessKey;
  private String secretKey;

  // Getters
  public String getRegion() {
    return region;
  }

  public String getBucketName() {
    return bucketName;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  // Setters
  public void setRegion(String region) {
    this.region = region;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
}
