package com.yodishtr.alignment_service.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class ResultSummary {

  // job details
  // private UUID jobI
  // private AlignmentJob.Tool tool;
  // private ExecutionMetrics executionMetrics;
  // job params
  private Double eValueCutoff;
  private String scoringMatrix;
  private Integer gapOpenPenalty;
  // result details
  private Integer totalHits;
  private Double topScore;
  private Double bottomScore;
  private Integer queryLength;
  private Long dbSequenceEvaluated;
  private Double bitScore;
  private Double identityPercentage;

  public ResultSummary() {
  }

  @JsonCreator
  public ResultSummary(@JsonProperty("eValueCutoff") Double eValueCutoff,
      @JsonProperty("scoringMatrix") String scoringMatrix,
      @JsonProperty("gapOpenPenalty") Integer gapOpenPenalty,
      @JsonProperty("totalHits") Integer totalHits,
      @JsonProperty("topScore") Double topScore,
      @JsonProperty("bottomScore") Double bottomScore,
      @JsonProperty("queryLength") Integer queryLength,
      @JsonProperty("dbSequenceEvaluated") Long dbSequenceEvaluated,
      @JsonProperty("bitScore") Double bitScore,
      @JsonProperty("identityPercentage") Double identityPercentage) {
    this.eValueCutoff = eValueCutoff;
    this.scoringMatrix = scoringMatrix;
    this.gapOpenPenalty = gapOpenPenalty;
    this.totalHits = totalHits;
    this.topScore = topScore;
    this.bottomScore = bottomScore;
    this.queryLength = queryLength;
    this.dbSequenceEvaluated = dbSequenceEvaluated;
    this.bitScore = bitScore;
    this.identityPercentage = identityPercentage;
  }

  public Double getEValueCutoff() {
    return eValueCutoff;
  }

  public String getScoringMatrix() {
    return scoringMatrix;
  }

  public Integer getGapOpenPenalty() {
    return gapOpenPenalty;
  }

  public Integer getTotalHits() {
    return totalHits;
  }

  public Double getTopScore() {
    return topScore;
  }

  public Double getBottomScore() {
    return bottomScore;
  }

  public Integer getQueryLength() {
    return queryLength;
  }

  public Long getDbSequenceEvaluated() {
    return dbSequenceEvaluated;
  }

  public Double getBitScore() {
    return bitScore;
  }

  public Double getIdentityPercentage() {
    return identityPercentage;
  }

  // Setters
  // public void setJobId(UUID jobId) {
  // this.jobId = jobId;
  // }
  //
  // public void setTool(AlignmentJob.Tool tool) {
  // this.tool = tool;
  // }
  //
  // public void setExecutionMetrics(ExecutionMetrics executionMetrics) {
  // this.executionMetrics = executionMetrics;
  // }

  public void setEValueCutoff(Double eValueCutoff) {
    this.eValueCutoff = eValueCutoff;
  }

  public void setScoringMatrix(String scoringMatrix) {
    this.scoringMatrix = scoringMatrix;
  }

  public void setGapOpenPenalty(Integer gapOpenPenalty) {
    this.gapOpenPenalty = gapOpenPenalty;
  }

  public void setTotalHits(Integer totalHits) {
    this.totalHits = totalHits;
  }

  public void setTopScore(Double topScore) {
    this.topScore = topScore;
  }

  public void setBottomScore(Double bottomScore) {
    this.bottomScore = bottomScore;
  }

  public void setQueryLength(Integer queryLength) {
    this.queryLength = queryLength;
  }

  public void setDbSequenceEvaluated(Long dbSequenceEvaluated) {
    this.dbSequenceEvaluated = dbSequenceEvaluated;
  }

  public void setBitScore(Double bitScore) {
    this.bitScore = bitScore;
  }

  public void setIdentityPercentage(Double identityPercentage) {
    this.identityPercentage = identityPercentage;
  }

}
