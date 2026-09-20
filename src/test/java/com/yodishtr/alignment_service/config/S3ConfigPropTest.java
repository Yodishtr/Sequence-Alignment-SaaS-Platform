package com.yodishtr.alignment_service.config;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = S3ConfigPropTest.TestConfig.class)
@TestPropertySource(properties = {
    "aws.s3.region=Canada",
    "aws.s3.bucket-name=blast",
    "aws.s3.access-key=key",
    "aws.s3.secret-key=secret"
})
public class S3ConfigPropTest {

  @Autowired
  private S3ConfigProp s3ConfigProp;

  @EnableConfigurationProperties(S3ConfigProp.class)
  static class TestConfig {
  }

  @Test
  public void testS3ConfigPropSetup() {
    assertAll("testing if s3 configs properties were setup correctly",
        () -> assertEquals("Canada", s3ConfigProp.getRegion()),
        () -> assertEquals("blast", s3ConfigProp.getBucketName()),
        () -> assertEquals("key", s3ConfigProp.getAccessKey()),
        () -> assertEquals("secret", s3ConfigProp.getSecretKey()));
  }
}
