package com.yodishtr.alignment_service.mapper;

import org.springframework.stereotype.Component;

import com.yodishtr.alignment_service.dto.JobSubmissionRequest;
import com.yodishtr.alignment_service.entity.AlignmentJob;
import com.yodishtr.alignment_service.entity.JobParameter;

@Component
public class RequestToJobMapper {

  // !!!!! service using this mapper needs to add job to tenant as well.

  public static AlignmentJob toEntity(JobSubmissionRequest request) {
    AlignmentJob job = new AlignmentJob(AlignmentJob.Tool.getToolFromString(request.getToolType()));
    JobParameter jobParameter = new JobParameter(request.getEValueCutoff(), request.getScoringMatrix(),
        request.getGapOpenPenalty());
    job.setJobParameter(jobParameter);
    job.setJobStatus(AlignmentJob.JobStatus.PENDING);
    return job;
  }
}
