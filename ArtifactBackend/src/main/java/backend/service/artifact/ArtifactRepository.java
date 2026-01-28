package backend.service.artifact;

import backend.service.data.ArtifactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Long> {

    boolean existsByRepositoryAndArtifactNameAndVersion(
            String repository,
            String artifactName,
            String version
    );

    Optional<ArtifactEntity> findByRepositoryAndArtifactNameAndVersion(
            String repository,
            String artifactName,
            String version
    );
}
