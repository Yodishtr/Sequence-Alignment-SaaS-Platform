package com.yodishtr.alignment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import com.yodishtr.alignment_service.entity.ApiKey;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

  public Optional<ApiKey> findByKeyHash(String keyHash);

  public List<ApiKey> findByKeyStatus(ApiKey.KeyStatus keyStatus);

  @EntityGraph(attributePaths = { "tenant" })
  public Optional<ApiKey> findApiKeyAlongTenantById(UUID apiKeyId);

  @EntityGraph(attributePaths = { "tenant" })
  public Optional<ApiKey> findApiKeyAlongTenantByKeyHash(String keyHash);

}
