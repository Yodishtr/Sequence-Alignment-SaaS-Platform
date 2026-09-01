package com.yodishtr.alignment_service.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "api_key")
@EntityListeners(AuditingEntityListener.class)
public class ApiKey {

    public enum KeyStatus {
        ACTIVE("active"),
        REVOKED("revoked"),
        EXPIRED("expired"),
        UNKNOWN("unknown");

        private String value;

        KeyStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private static Map<String, KeyStatus> lookupMap = new HashMap<>();
        static {
            for (KeyStatus keyStatus : KeyStatus.values()) {
                lookupMap.put(keyStatus.getValue(), keyStatus);
            }
        }

        public static KeyStatus getKeyStatusFromString(String key) {
            if (key == null || key.isBlank()) {
                return KeyStatus.UNKNOWN;
            }
            String sanitizedKey = key.trim().toLowerCase();
            return lookupMap.getOrDefault(sanitizedKey, KeyStatus.UNKNOWN);

        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "key_hash", nullable = false, unique = true)
    private String keyHash;

    @Column(name = "key_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private KeyStatus keyStatus;

    protected ApiKey() {}

    // Getters
    public UUID getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public KeyStatus getKeyStatus() {
        return keyStatus;
    }

    // Setters
    public void setKeyStatus(KeyStatus keyStatus) {
        this.keyStatus = keyStatus;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }
}
