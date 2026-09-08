package com.yodishtr.alignment_service.storage;

import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

// need to add the config lines to application.properties.
@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "s3")
public class S3Storage implements StorageUtility {

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
