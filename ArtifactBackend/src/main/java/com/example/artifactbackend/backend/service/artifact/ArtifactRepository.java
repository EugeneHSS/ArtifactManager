package com.example.artifactbackend.backend.service.artifact;

import com.example.artifactbackend.backend.service.data.ArtifactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
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

    List<ArtifactEntity> findByRepository(String repository);


    List<ArtifactEntity> findByRepositoryAndArtifactName(String repository, String artifactName);
}
