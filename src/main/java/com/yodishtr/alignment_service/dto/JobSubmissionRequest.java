package com.yodishtr.alignment_service.dto;

public class JobSubmissionRequest {
  private String sequenceData;
  private String toolType;
  private String targetDatabase;
  private Double eValueCutoff;
  private String scoringMatrix;
  private Integer gapOpenPenalty;

  public JobSubmissionRequest() {
  }

  public JobSubmissionRequest(String sequenceData, String toolType, String targetDatabase,
      Double eValueCutOff, String scoringMatrix, Integer gapOpenPenalty) {
    this.sequenceData = sequenceData;
    this.toolType = toolType;
    this.targetDatabase = targetDatabase;
    this.eValueCutoff = eValueCutOff;
    this.scoringMatrix = scoringMatrix;
    this.gapOpenPenalty = gapOpenPenalty;
  }

  // Getters
  public String getSequenceData() {
    return this.sequenceData;
  }

  public String getToolType() {
    return this.toolType;
  }

  public String getTargetDatabase() {
    return this.targetDatabase;
  }

  public Double getEValueCutoff() {
    return this.eValueCutoff;
  }

  public String getScoringMatrix() {
    return this.scoringMatrix;
  }

  public Integer getGapOpenPenalty() {
    return this.gapOpenPenalty;
  }

  // Setters
  public void setSequenceData(String seqData) {
    this.sequenceData = seqData;
  }

  public void setToolType(String toolType) {
    this.toolType = toolType;
  }

  public void setTargetDatabase(String targetDatabase) {
    this.targetDatabase = targetDatabase;
  }

  public void setEValueCutoff(Double eValueCutoff) {
    this.eValueCutoff = eValueCutoff;
  }

  public void setScoringMatrix(String scoringMatrix) {
    this.scoringMatrix = scoringMatrix;
  }

  public void setGapOpenPenalty(Integer gapOpenPenalty) {
    this.gapOpenPenalty = gapOpenPenalty;
  }

}
