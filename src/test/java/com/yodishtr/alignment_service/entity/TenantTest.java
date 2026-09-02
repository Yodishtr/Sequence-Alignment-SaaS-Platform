package com.yodishtr.alignment_service.entity;

import com.yodishtr.alignment_service.repository.AlignmentJobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.yodishtr.alignment_service.repository.TenantRepository;

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
                () -> assertEquals(0, optionalTenant.get().getAlignmentJobs().size()),
                () -> assertEquals(0, optionalTenant.get().getApiKeys().size()),
                () -> assertEquals(1, optionalTenant.get().getQuota())
        );
    }

    @Test
    public void jobBelongsToTenant() {
        Tenant tenant = new Tenant();
        tenant.setName("test");
        tenant.setQuota(1);
        AlignmentJob job = new AlignmentJob();
        job.setJobStatus(AlignmentJob.JobStatus.UNKNOWN);
        tenant.addAlignmentJob(job);
        entityManager.persistAndFlush(tenant);
        Optional<Tenant> optionalTenantFirst = tenantRepository.findByName("test");
        Optional<Tenant> optionalTenantSecond = tenantRepository.findTenantByName("test");
        List<AlignmentJob> jobsList = alignmentJobRepository.findByJobStatus(AlignmentJob.JobStatus.UNKNOWN);

        assertAll("testing the helper",
                () -> assertTrue(optionalTenantFirst.isPresent(), "first optional failed"),
                () -> assertTrue(optionalTenantSecond.isPresent(), "second optional failed"),
                () -> assertTrue(jobsList.size() == 1, "job list size is not 1"),
                () -> assertEquals("test", optionalTenantFirst.get().getName()),
                () -> assertEquals("test", optionalTenantSecond.get().getName()),
                () -> assertTrue(optionalTenantFirst.get().getAlignmentJobs().size() == 1, "first optional tenant does "),
                () -> assertTrue(optionalTenantSecond.get().getAlignmentJobs().size() == 1),
                () -> assertTrue(jobsList.getFirst().getTenant().getName().equals("test")),
                () -> assertTrue(jobsList.getFirst().getTenant().getId().equals(tenant.getId()))
                );

    }


}
