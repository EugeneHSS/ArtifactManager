package com.example.artifactbackend.backend.controller;


import com.example.artifactbackend.backend.service.artifact.ArtifactService;
import com.example.artifactbackend.backend.service.data.ArtifactEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<ArtifactEntity> getArtifact(@PathVariable Long id) {
        return ResponseEntity.ok(artifactService.getById(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
        InputStream data = artifactService.download(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                .body(new InputStreamResource(data));
    }

    @PostMapping("/{id}/deprecate")
    public ResponseEntity<ArtifactEntity> deprecate(@PathVariable Long id) {
        ArtifactEntity artifact = artifactService.getById(id);
        return ResponseEntity.ok(artifactService.deprecate(artifact));
    }

    @GetMapping
    public ResponseEntity<List<ArtifactEntity>> listByRepository(@RequestParam String repository)
    {
        return ResponseEntity.ok(
                artifactService.listByRepository(repository)
        );
    }

}
