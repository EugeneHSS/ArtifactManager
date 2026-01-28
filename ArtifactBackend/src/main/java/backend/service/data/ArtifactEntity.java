package backend.service.data;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "artifacts")
public class ArtifactEntity {

    protected ArtifactEntity() {
    }

    public ArtifactEntity(
            String repository,
            String artifactName,
            String version,
            String checksum
    ) {
        this.repository = repository;
        this.artifactName = artifactName;
        this.version = version;
        this.checksum = checksum;
        this.status = ArtifactStatus.STAGING;
        this.createdAt = Instant.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String artifactName;

    @Column(nullable = false)
    private String repository;

    @Column(nullable = false)
    private String version;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtifactStatus status;

    @Column(nullable = false, length = 64)
    private String checksum;

    @Column(nullable = false)
    private Instant createdAt;

    public Long getId() { return id; }
    public String getArtifactName() { return artifactName; }
    public String getRepository() { return repository; }
    public String getVersion() { return version; }
    public String getChecksum() { return checksum; }
    public Instant getCreatedAt() { return createdAt; }

    public ArtifactStatus getStatus() { return status; }
    public void setStatus(ArtifactStatus status) {
        this.status = status;
    }
}

