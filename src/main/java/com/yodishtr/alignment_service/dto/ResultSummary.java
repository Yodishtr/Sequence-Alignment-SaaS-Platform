package com.yodishtr.alignment_service.dto;

public class ResultSummary {
    // job details
//    private UUID jobId;
//    private AlignmentJob.Tool tool;
//    private ExecutionMetrics executionMetrics;
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

    public ResultSummary() {}

    // Getters
//    public UUID getJobId() {
//        return jobId;
//    }
//
//    public AlignmentJob.Tool getTool() {
//        return tool;
//    }
//
//    public ExecutionMetrics getExecutionMetrics() {
//        return executionMetrics;
//    }

    public Double geteValueCutoff() {
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
//    public void setJobId(UUID jobId) {
//        this.jobId = jobId;
//    }
//
//    public void setTool(AlignmentJob.Tool tool) {
//        this.tool = tool;
//    }
//
//    public void setExecutionMetrics(ExecutionMetrics executionMetrics) {
//        this.executionMetrics = executionMetrics;
//    }

    public void seteValueCutoff(Double eValueCutoff) {
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
