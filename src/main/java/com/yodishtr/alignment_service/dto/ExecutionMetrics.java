package com.yodishtr.alignment_service.dto;

public class ExecutionMetrics {

    private Long executionDurationMs;
    private Long memoryConsumedBytes;
    private Long cpuTimeNs;
    private Integer exitCode;

    public ExecutionMetrics(Long executionDurationMs, Long cpuTimeNs, Integer exitCode, Long memoryConsumedBytes) {
        this.executionDurationMs = executionDurationMs;
        this.cpuTimeNs = cpuTimeNs;
        this.exitCode = exitCode;
        this.memoryConsumedBytes = memoryConsumedBytes;
    }

    public ExecutionMetrics() {}

    // Getters
    public Long getExecutionDurationMs() {
        return executionDurationMs;
    }

    public Long getMemoryConsumedBytes() {
        return memoryConsumedBytes;
    }

    public Long getCpuTimeNs() {
        return cpuTimeNs;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    // Setters
    public void setExecutionDurationMs(Long executionDurationMs) {
        this.executionDurationMs = executionDurationMs;
    }

    public void setMemoryConsumedBytes(Long memoryConsumedBytes) {
        this.memoryConsumedBytes = memoryConsumedBytes;
    }

    public void setCpuTimeNs(Long cpuTimeNs) {
        this.cpuTimeNs = cpuTimeNs;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }
}
