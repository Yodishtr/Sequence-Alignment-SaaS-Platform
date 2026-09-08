package com.yodishtr.alignment_service.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobParameter {

  /*
   * need to have fields checked in service layer
   * because db wont do the check before saving it as json
   * also need to implement a hashcode or equals method so that when a job
   * parameter is to
   * be changed it will be persisted instead of hibernate going over it
   */
  private Double eValueCutoff;
  private String scoringMatrix;
  private Integer gapOpenPenalty;

  @JsonCreator
  public JobParameter(@JsonProperty("eValueCutoff") Double eValueCutoff,
      @JsonProperty("scoringMatrix") String scoringMatrix,
      @JsonProperty("gapOpenPenalty") Integer gapOpenPenalty) {
    this.eValueCutoff = eValueCutoff;
    this.scoringMatrix = scoringMatrix;
    this.gapOpenPenalty = gapOpenPenalty;
  }

  public JobParameter() {
  }

  // Getter
  public Double getEValueCutoff() {
    return eValueCutoff;
  }

  public String getScoringMatrix() {
    return scoringMatrix;
  }

  public Integer getGapOpenPenalty() {
    return gapOpenPenalty;
  }

  // Setter
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
