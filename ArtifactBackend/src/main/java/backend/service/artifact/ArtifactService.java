package backend.service.artifact;

import backend.service.data.ArtifactEntity;
import backend.service.data.ArtifactStatus;
import backend.service.storage.IBlobStore;
import backend.utils.HashUtils;
import jakarta.transaction.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ArtifactService {

    private final ArtifactRepository repository;
    private final IBlobStore storageBlob;

    public ArtifactService(ArtifactRepository repo, IBlobStore blobStore)
    {
        this.repository = repo;
        this.storageBlob = blobStore;
    }

    @Transactional
    public ArtifactEntity createArtifact(
            String repositoryName,
            String name,
            String version,
            InputStream data
    ) throws IOException {

        if (repository.existsByRepositoryAndArtifactNameAndVersion(
                repositoryName, name, version)) {
            throw new IllegalStateException("Artifact version already exists");
        }

        byte[] bytes = data.readAllBytes();

        String checksum = HashUtils.computeSha256(
                new ByteArrayInputStream(bytes)
        );

        storageBlob.store(
                checksum,
                new ByteArrayInputStream(bytes)
        );

        try {
            ArtifactEntity artifact = new ArtifactEntity(
                    repositoryName,
                    name,
                    version,
                    checksum
            );

            return repository.save(artifact);

        } catch (Exception e) {
            storageBlob.delete(checksum);
            throw e;
        }
    }



    public ArtifactEntity promoteToRelease(ArtifactEntity artifact)
    {
        if (artifact.getStatus() != ArtifactStatus.STAGING)
        {
            throw new IllegalStateException("Only STAGING artifacts can be released");
        }

        artifact.setStatus(ArtifactStatus.RELEASED);
        return repository.save(artifact);
    }

    public ArtifactEntity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artifact not found"));
    }


}
