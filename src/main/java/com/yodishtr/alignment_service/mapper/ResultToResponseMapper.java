package com.yodishtr.alignment_service.mapper;

import com.yodishtr.alignment_service.dto.JobResponse;
import com.yodishtr.alignment_service.entity.ResultSummary;

public class ResultToResponseMapper {

  public static JobResponse fromResultSummary(ResultSummary result) {
    JobResponse response = new JobResponse();
    response.setEValueCutoff(result.getEValueCutoff());
    response.setScoringMatrix(result.getScoringMatrix());
    response.setGapOpenPenalty(result.getGapOpenPenalty());
    response.setTotalHits(result.getTotalHits());
    response.setTopScore(result.getTopScore());
    response.setBottomScore(result.getBottomScore());
    response.setQueryLength(result.getQueryLength());
    response.setDbSequenceEvaluated(result.getDbSequenceEvaluated());
    response.setBitScore(result.getBitScore());
    response.setIdentityPercentage(result.getIdentityPercentage());
    return response;
  }
}
