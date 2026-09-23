package com.yodishtr.alignment_service.storage;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;

import java.util.UUID;
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
    expected.append(myWorkspace.toAbsolutePath().toString()).append(knownUUID.toString()).append(".fasta");
    assertAll("File Reference ID correctly returned",
        () -> assertTrue(Files.exists(myWorkspace.resolve(knownUUID.toString() + ".fasta"))),
        () -> assertEquals(expected.toString(), returnedReference));
  }

  @Test
  public void testSequenceIsSavedCorrectly() {
    String returnedReference = localStorage.saveSequence(sequenceData, targetDatabase, knownUUID);
  }
}
