package com.yodishtr.alignment_service.dto;

public class JobParameter {
    /* need to have fields checked in service layer
    * because db wont do the check before saving it as json */
    private Double eValueCutoff;
    private String scoringMatrix;
    private Integer gapOpenPenalty;

    public JobParameter(Double eValueCutoff, String scoringMatrix, Integer gapOpenPenalty) {
        this.eValueCutoff = eValueCutoff;
        this.scoringMatrix = scoringMatrix;
        this.gapOpenPenalty = gapOpenPenalty;
    }

    public JobParameter() {}

    // Getter
    public Double geteValueCutoff() {
        return eValueCutoff;
    }

    public String getScoringMatrix() {
        return scoringMatrix;
    }

    public Integer getGapOpenPenalty() {
        return gapOpenPenalty;
    }

    // Setter
    public void seteValueCutoff(Double eValueCutoff) {
        this.eValueCutoff = eValueCutoff;
    }

    public void setScoringMatrix(String scoringMatrix) {
        this.scoringMatrix = scoringMatrix;
    }

    public void setGapOpenPenalty(Integer gapOpenPenalty) {
        this.gapOpenPenalty = gapOpenPenalty;
    }

}
