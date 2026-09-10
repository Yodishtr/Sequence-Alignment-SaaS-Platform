package com.yodishtr.alignment_service.storage;

import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.yodishtr.alignment_service.config.RootDirConfig;
import jakarta.annotation.PostConstruct;

// need to add the config lines to application.properties.
@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "local")
public class LocalStorage implements StorageUtility {

  private RootDirConfig rootDir;

  public LocalStorage(RootDirConfig rootDir) {
    this.rootDir = rootDir;
  }

  @PostConstruct
  public void initLocalStorageFolder() {
    Path storagePath = Paths.get(rootDir.getRootDirectory());
    if (!Files.exists(storagePath)) {
      try {
        Files.createDirectories(storagePath);
      } catch (IOException e) {
        throw new RuntimeException("Could not initialize local storage folder");
      }
    }
  }

  @Override
  public String saveSequence(String sequenceData, String targetDatabase, UUID jobIdentifier) {

    return "";
  }

  @Override
  public String readSequence(String inputReference) {
    return "";
  }

  @Override
  public boolean deleteSequence(String inputReference) {
    return false;
  }

  @Override
  public boolean exists(String inputReference) {
    return false;
  }
}
