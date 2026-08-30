package entity;

import dto.JobParameter;
import dto.ResultSummary;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "alignment_job")
@EntityListeners(AuditingEntityListener.class)
public class AlignmentJob {

    public enum JobStatus {
        PENDING("pending"),
        RUNNING("running"),
        COMPLETED("completed"),
        CANCELLED("cancelled"),
        FAILED("failed"),
        UNKNOWN("unknown");

        private String value;

        JobStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private static Map<String, JobStatus> lookupMap = new HashMap<>();

        static {
            for (JobStatus jobStatus : JobStatus.values()) {
                lookupMap.put(jobStatus.getValue(), jobStatus);
            }
        }

        public static JobStatus getJobStatus(String value) {
            if (value == null || value.isBlank()) {
                return JobStatus.UNKNOWN;
            }
            String sanitizedValue = value.trim().toLowerCase();
            return lookupMap.getOrDefault(sanitizedValue, JobStatus.UNKNOWN);
        }
    }

    public enum Tool {
        BLAST("blast"),
        SMITHWATERMAN("smith-waterman"),
        UNKNOWN("unknown");

        private final String value;

        Tool(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private static Map<String, Tool> lookupMap = new HashMap<>();
        static {
            for (Tool tool : Tool.values()) {
                lookupMap.put(tool.getValue(), tool);
            }
        }

        public static Tool getToolFromString(String value) {
            if (value == null || value.isBlank()) {
                return Tool.UNKNOWN;
            }
            String sanitizedValue = value.trim().toLowerCase();
            return lookupMap.getOrDefault(sanitizedValue, Tool.UNKNOWN);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "job_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    @Column(name = "input_reference")
    private String inputReference;

    @Column(name = "tool", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tool tool;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "job_parameter")
    private JobParameter jobParameter;


    /* !!!!REMEMBER: result summary dto contains execution metrics dto.
    * this needs to be updated accordingly when a job is created.*/
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "result")
    private ResultSummary resultSummary;

    @CreatedDate
    @Column(name = "created_at",nullable = false, updatable = false)
    private Instant createdAt;


    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "error_message")
    private String errorMessage;

    protected AlignmentJob() {}

    // Getters
    public UUID getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public String getInputReference() {
        return inputReference;
    }

    public Tool getTool() {
        return tool;
    }

    public JobParameter getJobParameter() {
        return jobParameter;
    }

    public ResultSummary getResultSummary() {
        return resultSummary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public void setInputReference(String inputReference) {
        this.inputReference = inputReference;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setJobParameter(JobParameter jobParameter) {
        this.jobParameter = jobParameter;
    }

    public void setResultSummary(ResultSummary resultSummary) {
        this.resultSummary = resultSummary;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

