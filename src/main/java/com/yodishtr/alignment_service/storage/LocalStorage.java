package com.yodishtr.alignment_service.storage;

import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.OutputStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.yodishtr.alignment_service.config.RootDirConfig;
import jakarta.annotation.PostConstruct;

// need to add the config lines to application.properties.
@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "local")
public class LocalStorage implements StorageUtility {

  private static final Logger log = LoggerFactory.getLogger(LocalStorage.class);
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
    Path currentFastaSaveFile = Path.of(rootDir.getRootDirectory(), jobIdentifier.toString() + ".fasta");
    try {
      Files.createFile(currentFastaSaveFile);
    } catch (IOException e) {
      log.error("Failed to create file in path: ", currentFastaSaveFile, e);
      throw new StorageException("unable to create file", e);
    }

    try (OutputStream outputStream = Files.newOutputStream(currentFastaSaveFile);
        BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
      buffWriter.write("targetDatabase=");
      buffWriter.write(targetDatabase);
      buffWriter.write("sequence=");
      buffWriter.write(sequenceData);
    } catch (IOException e) {
      log.error("Unable to write to file at path: ", currentFastaSaveFile, e);
      throw new StorageException("unable to write to file", e);
    }

    return currentFastaSaveFile.toString();
  }

  @Override
  public String readSequence(String inputReference) {
    Path savedJobFile = Path.of(inputReference);
    String retrieveSequence = "";
    try (InputStream inputStream = Files.newInputStream(savedJobFile);
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = buffReader.readLine()) != null) {
        if (line.startsWith("targetDatabase")) {
          continue;
        } else if (line.startsWith("sequence")) {
          String[] arr = line.split("=", 2);
          retrieveSequence = arr[1];
        }
      }
    } catch (IOException e) {
      log.error("cannot read file at path: ", savedJobFile, e);
      throw new StorageException("unable to read file", e);
    }
    return retrieveSequence;
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
