package com.yodishtr.alignment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yodishtr.alignment_service.entity.AlignmentJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlignmentJobRepository extends JpaRepository<AlignmentJob, Long> {

    public List<AlignmentJob> findByTool(String tool);
    public List<AlignmentJob> findByJobStatus(AlignmentJob.JobStatus jobStatus);

    @Query("SELECT aj FROM AlignmentJob aj WHERE aj.createdAt = ?1")
    public List<AlignmentJob> findByCreatedAtDate(Instant createdAtDate);

    @Query("SELECT aj FROM AlignmentJob aj WHERE aj.tenant = :tenantId")
    public List<AlignmentJob> findByTenant(@Param("tenantId") UUID tenantId);

}
