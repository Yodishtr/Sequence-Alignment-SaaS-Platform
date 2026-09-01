package com.yodishtr.alignment_service.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tenants")
@EntityListeners(AuditingEntityListener.class)
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tenant_name", nullable = false, unique = true)
    private String name;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "quota", nullable = false, updatable = true)
    private Integer quota;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private List<AlignmentJob> alignmentJobs = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApiKey> apiKeys = new ArrayList<>();

    protected Tenant() {}

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Integer getQuota() {
        return quota;
    }

    public List<AlignmentJob> getAlignmentJobs() {
        return alignmentJobs;
    }

    public List<ApiKey> getApiKeys() {
        return apiKeys;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public void setAlignmentJobs(List<AlignmentJob> alignmentJobs) {
        this.alignmentJobs = alignmentJobs;
    }

    public void setApiKeys(List<ApiKey> apiKeys) {
        this.apiKeys = apiKeys;
    }


    // JPA helper
    public void addAlignmentJob(AlignmentJob alignmentJob) {
        this.alignmentJobs.add(alignmentJob);
        alignmentJob.setTenant(this);
    }

    public void addApiKey(ApiKey apiKey) {
        this.apiKeys.add(apiKey);
        apiKey.setTenant(this);
    }
}
