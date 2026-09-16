package com.yodishtr.alignment_service.storage;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.yodishtr.alignment_service.config.S3ConfigProp;
import java.util.Map;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// need to add the config lines to application.properties.
@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "s3")
public class S3Storage implements StorageUtility {

  private static final String FASTA_FOLDER = "dna-sequence/";

  private final S3Client s3Client;
  private final S3ConfigProp s3ConfigProp;

  public S3Storage(S3Client s3Client, S3ConfigProp s3ConfigProp) {
    this.s3Client = s3Client;
    this.s3ConfigProp = s3ConfigProp;
  }

  @Override
  public boolean exists(String inputReference) {
    try {
      HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
          .bucket(s3ConfigProp.getBucketName())
          .key(inputReference)
          .build();
      s3Client.headObject(headObjectRequest);
      return true;
    } catch (NoSuchKeyException e) {
      return false;
    }
  }

  @Override
  public String saveSequence(String sequenceData, String targetDatabase, UUID jobIdentifier) {
    String fullKey = FASTA_FOLDER + jobIdentifier.toString();
    byte[] contentBytes = sequenceData.getBytes(StandardCharsets.UTF_8);
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(s3ConfigProp.getBucketName())
        .key(fullKey)
        .contentType("text/plain")
        .metadata(Map.of("targetDatabase", targetDatabase))
        .build();
    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(contentBytes));
    return fullKey;
  }

  @Override
  public String readSequence(String inputReference) {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(s3ConfigProp.getBucketName())
        .key(inputReference)
        .build();
    ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
    String fastaContent = responseBytes.asString(StandardCharsets.UTF_8);
    return fastaContent;
  }

  @Override
  public boolean deleteSequence(String inputReference) {
    if (!exists(inputReference)) {
      throw new IllegalArgumentException("Cannot delete file: File does not exist in s3");
    }
    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
        .bucket(s3ConfigProp.getBucketName())
        .key(inputReference)
        .build();
    s3Client.deleteObject(deleteObjectRequest);
    return true;
  }

}
