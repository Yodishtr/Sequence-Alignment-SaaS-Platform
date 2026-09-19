package com.yodishtr.alignment_service.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RootDirConfigTest.TestConfig.class)
@TestPropertySource(properties = { "file.storage.data.local.root-directory=/test/root" })
public class RootDirConfigTest {

  @Autowired
  private RootDirConfig rootDirConfig;

  @Configuration
  @EnableConfigurationProperties(RootDirConfig.class)
  static class TestConfig {
  }

  @Test
  public void correctRootDir() {
    String retrieved = rootDirConfig.getRootDirectory();
    assertEquals("/test/root", retrieved);
  }

}
