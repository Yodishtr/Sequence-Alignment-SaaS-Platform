package com.yodishtr.alignment_service.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ServiceClientConfiguration;

public class S3ClientConfigTest {

  @Test
  public void s3ClientCreationTest() {
    S3ConfigProp s3ConfigProp = new S3ConfigProp();
    s3ConfigProp.setRegion("ca-central-1");
    s3ConfigProp.setBucketName("blast");
    s3ConfigProp.setAccessKey("key");
    s3ConfigProp.setSecretKey("secret");

    S3ClientConfig s3ClientConfig = new S3ClientConfig(s3ConfigProp);
    S3Client currentS3Client = s3ClientConfig.s3Client();
    S3ServiceClientConfiguration s3ClientConfiguration = currentS3Client.serviceClientConfiguration();

    assertAll("S3 assertion tests",
        () -> assertEquals("ca-central-1", s3ClientConfiguration.region().id()));
  }
}
