package com.yodishtr.alignment_service.storage;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;

import java.util.UUID;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.yodishtr.alignment_service.config.RootDirConfig;

public class LocalStorageTest {

  @TempDir
  Path myWorkspace;

  RootDirConfig rootDirConfig;
  LocalStorage localStorage;
  UUID knownUUID;
  String sequenceData;
  String targetDatabase;

  @BeforeEach
  void setUp() {
    rootDirConfig = new RootDirConfig();
    rootDirConfig.setRootDirectory(myWorkspace.toAbsolutePath().toString());
    localStorage = new LocalStorage(rootDirConfig);
    localStorage.initLocalStorageFolder();
    knownUUID = UUID.fromString("12345678-1234-1234-1234-123456789abc");
    sequenceData = "ATGC";
    targetDatabase = "someDB";
  }

  @Test
  public void testInitLocalStorageFolder() {
    assertTrue(Files.exists(myWorkspace));
  }

  @Test
  public void testSaveSequenceReturnsCorrectReference() {
    String returnedReference = localStorage.saveSequence(sequenceData, targetDatabase, knownUUID);
    StringBuilder expected = new StringBuilder();
    expected.append(myWorkspace.toAbsolutePath().toString()).append("/").append(knownUUID.toString()).append(".fasta");
    assertAll("File Reference ID correctly returned",
        () -> assertTrue(Files.exists(myWorkspace.resolve(knownUUID.toString() + ".fasta"))),
        () -> assertEquals(expected.toString(), returnedReference));
  }

  @Test
  public void testSequenceIsSavedCorrectly() throws IOException {
    String returnedReference = localStorage.saveSequence(sequenceData, targetDatabase, knownUUID);
    String content = Files.readString(Path.of(returnedReference));
    assertTrue(!content.isEmpty());
  }

  @Test
  public void testWrittenCorrectly() throws IOException {
    String returnedRef = localStorage.saveSequence(sequenceData, targetDatabase, knownUUID);
    String content = Files.readString(Path.of(returnedRef));
    String[] contentArray = content.split("\\n");
    String targetDB = contentArray[0].split("=")[1];
    String seqData = contentArray[1].split("=")[1];
    assertAll("written correctly",
        () -> assertEquals(targetDatabase, targetDB),
        () -> assertEquals(sequenceData, seqData));
  }
}
