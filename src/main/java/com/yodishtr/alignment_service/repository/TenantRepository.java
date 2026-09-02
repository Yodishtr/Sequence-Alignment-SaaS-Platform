package com.yodishtr.alignment_service.repository;

import com.yodishtr.alignment_service.entity.Tenant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    public Optional<Tenant> findByName(String name);
    public List<Tenant> findByCreatedAt(Instant createdDate);

    @EntityGraph(attributePaths = {"alignmentJobs"})
    public Optional<Tenant> findTenantById(String tenantId);

    @EntityGraph(attributePaths = {"alignmentJobs"})
    public Optional<Tenant> findTenantByName(String name);

}
