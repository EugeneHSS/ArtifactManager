package backend.controller;


import backend.service.artifact.ArtifactService;
import backend.service.data.ArtifactEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/artifacts")
public class ArtifactController {
    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService artifactServiceInst)
    {
        this.artifactService = artifactServiceInst;
    }

    @PostMapping("/upload")
    public ResponseEntity<ArtifactEntity> uploadArtifact(
            @RequestParam String repository,
            @RequestParam String name,
            @RequestParam String version,
            @RequestParam MultipartFile file
    ) throws IOException
    {
        ArtifactEntity artifact = artifactService.createArtifact(
                repository, name, version, file.getInputStream()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(artifact);
    }

    @PostMapping("{id}/release")
    public ResponseEntity<ArtifactEntity> promoteToRelease(
            @PathVariable Long id
    )
    {
        ArtifactEntity artifact = artifactService.promoteToRelease(artifactService.getById(id));
        return ResponseEntity.ok(artifact);
    }
}
