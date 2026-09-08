package com.yodishtr.alignment_service.storage;

import java.util.UUID;

public interface StorageUtility {

  String saveSequence(String sequenceData, String targetDatabase, UUID jobIdentifier);

  String readSequence(String inputReference);

  boolean deleteSequence(String inputReference);

  boolean exists(String inputReference);
}
