package entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "alignment_job")
public class AlignmentJob {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
