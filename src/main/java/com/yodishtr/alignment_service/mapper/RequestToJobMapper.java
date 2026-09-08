package com.yodishtr.alignment_service.mapper;

import org.springframework.stereotype.Component;

import com.yodishtr.alignment_service.dto.JobSubmissionRequest;
import com.yodishtr.alignment_service.entity.AlignmentJob;
import com.yodishtr.alignment_service.entity.JobParameter;
import com.yodishtr.alignment_service.entity.Tenant;

@Component
public class RequestToJobMapper {

  // !!!!! service using this mapper needs to add job to tenant as well.

  public static AlignmentJob toEntity(JobSubmissionRequest request, String filePathReference, Tenant tenant) {
    AlignmentJob job = new AlignmentJob(tenant);
    job.setTool(AlignmentJob.Tool.getToolFromString(request.getToolType()));
    JobParameter jobParameter = new JobParameter(request.getEValueCutoff(), request.getScoringMatrix(),
        request.getGapOpenPenalty());
    job.setJobParameter(jobParameter);
    job.setInputReference(filePathReference);
    job.setJobStatus(AlignmentJob.JobStatus.PENDING);
    return job;
  }
}
