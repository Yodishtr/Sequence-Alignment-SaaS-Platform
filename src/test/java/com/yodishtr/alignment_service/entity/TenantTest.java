package com.yodishtr.alignment_service.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.yodishtr.alignment_service.repository.TenantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class TenantTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TenantRepository tenantRepository;

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


}
