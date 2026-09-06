package com.yodishtr.alignment_service.entity;

import com.yodishtr.alignment_service.repository.AlignmentJobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.yodishtr.alignment_service.repository.TenantRepository;
import com.yodishtr.alignment_service.repository.ApiKeyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class TenantTest {

  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private TenantRepository tenantRepository;
  @Autowired
  private AlignmentJobRepository alignmentJobRepository;
  @Autowired
  private ApiKeyRepository apiKeyRepository;

  @Test
  public void saveAndPersistSimpleTenant() {
    Tenant tenant = new Tenant();
    tenant.setName("test");
    tenant.setQuota(1);
    entityManager.persistAndFlush(tenant);
    Optional<Tenant> optionalTenant = tenantRepository.findByName("test");
    assertAll("Check if tenant object is persisted correctly",
        () -> assertTrue(optionalTenant.isPresent()),
        () -> assertEquals("test", optionalTenant.get().getName()),
        () -> assertTrue(optionalTenant.get().getId().equals(tenant.getId())),
        () -> assertEquals(0, optionalTenant.get().getAlignmentJobs().size()),
        () -> assertEquals(0, optionalTenant.get().getApiKeys().size()),
        () -> assertEquals(1, optionalTenant.get().getQuota()));
  }

  @Test
  public void jobBelongsToTenant() {
    Tenant tenant = new Tenant();
    tenant.setName("test");
    tenant.setQuota(1);
    AlignmentJob job = new AlignmentJob();
    job.setJobStatus(AlignmentJob.JobStatus.UNKNOWN);
    job.setTool(AlignmentJob.Tool.BLAST);
    tenant.addAlignmentJob(job);
    entityManager.persistAndFlush(tenant);
    entityManager.persistAndFlush(job);
    entityManager.clear();
    Optional<Tenant> optionalTenantFirst = tenantRepository.findByName("test");
    Optional<Tenant> optionalTenantSecond = tenantRepository.findTenantByName("test");
    List<AlignmentJob> jobsList = alignmentJobRepository.findByJobStatus(AlignmentJob.JobStatus.UNKNOWN);

    assertAll("testing the helper",
        () -> assertTrue(optionalTenantFirst.isPresent(), "first optional failed"),
        () -> assertTrue(optionalTenantSecond.isPresent(), "second optional failed"),
        () -> assertEquals(1, jobsList.size(), "job list size is the actual value generated."),
        () -> assertEquals("test", optionalTenantFirst.get().getName()),
        () -> assertEquals("test", optionalTenantSecond.get().getName()),
        () -> assertTrue(optionalTenantFirst.get().getAlignmentJobs().size() == 1, "first optional tenant does "),
        () -> assertTrue(optionalTenantSecond.get().getAlignmentJobs().size() == 1),
        () -> assertTrue(jobsList.getFirst().getTenant().getName().equals("test")),
        () -> assertTrue(jobsList.getFirst().getTenant().getId().equals(tenant.getId())));

  }

  @Test
  public void apiKeyTenantHelper() {
    Tenant currentTenant = new Tenant();
    currentTenant.setName("testTenant");
    currentTenant.setQuota(1);
    ApiKey apiKey = new ApiKey();
    apiKey.setTenant(currentTenant);
    apiKey.setKeyHash("keyHash");
    apiKey.setKeyStatus(ApiKey.KeyStatus.ACTIVE);
    currentTenant.addApiKey(apiKey);
    entityManager.persistAndFlush(currentTenant);
    entityManager.persistAndFlush(apiKey);
    entityManager.clear();
    Optional<Tenant> optionalTenant = tenantRepository.findByName("testTenant");
    Optional<ApiKey> optionalApiKey = apiKeyRepository.findApiKeyAlongTenantById(apiKey.getId());
    Optional<ApiKey> optionalApiKeySecond = apiKeyRepository.findApiKeyAlongTenantByKeyHash("keyHash");

    assertAll("test api key",
        () -> assertTrue(optionalTenant.isPresent(), "tenant not isPresent"),
        () -> assertTrue(optionalApiKey.isPresent(), "first api key optional object is absent"),
        () -> assertTrue(optionalApiKeySecond.isPresent(), "second api key optional is absent"),
        () -> assertTrue(optionalTenant.get().getApiKeys().size() == 1, "tenant does not contain api key"),
        () -> assertTrue(optionalTenant.get().getApiKeys().getFirst().getKeyHash().equals("keyHash")),
        () -> assertTrue(optionalApiKeySecond.get().getTenant().getName().equals("testTenant")));

  }

  @Test
  public void entitiesEnumPersist() {
    // Tenant object setup
    Tenant currentTenant = new Tenant();
    currentTenant.setName("test");
    currentTenant.setQuota(1);
    // AlignmentJob object
    AlignmentJob alignmentJob = new AlignmentJob();
    alignmentJob.setTenant(currentTenant);
    alignmentJob.setJobStatus(AlignmentJob.JobStatus.getJobStatus("running"));
    alignmentJob.setTool(AlignmentJob.Tool.getToolFromString("blast"));
    // ApiKey setup
    ApiKey apiKey = new ApiKey();
    apiKey.setTenant(currentTenant);
    apiKey.setKeyHash("okokok");
    apiKey.setKeyStatus(ApiKey.KeyStatus.getKeyStatusFromString("active"));
    // inverse side setup
    currentTenant.addAlignmentJob(alignmentJob);
    currentTenant.addApiKey(apiKey);
    entityManager.persistAndFlush(currentTenant);
    entityManager.persistAndFlush(alignmentJob);
    entityManager.persistAndFlush(apiKey);
    entityManager.clear();
    Optional<AlignmentJob> optionalJob = alignmentJobRepository.findById(alignmentJob.getId());
    Optional<ApiKey> optionalKey = apiKeyRepository.findById(apiKey.getId());
    // Assertions
    assertAll("checking enum pesistence",
        () -> assertTrue(optionalJob.isPresent()),
        () -> assertTrue(optionalKey.isPresent()),
        () -> assertTrue(optionalJob.get().getJobStatus() == AlignmentJob.JobStatus.RUNNING),
        () -> assertTrue(optionalJob.get().getTool() == AlignmentJob.Tool.BLAST),
        () -> assertTrue(optionalKey.get().getKeyStatus() == ApiKey.KeyStatus.ACTIVE));
  }

}
