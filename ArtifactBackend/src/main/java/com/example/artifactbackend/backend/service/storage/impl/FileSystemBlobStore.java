package com.example.artifactbackend.backend.service.storage.impl;

import com.example.artifactbackend.backend.service.storage.IBlobStore;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
class FileSystemBlobStore implements IBlobStore
{
    private final Path root = Paths.get("data/blobs");

    @Override
    public void store(String checksum, InputStream data) throws IOException
    {
        Files.createDirectories(root);
        Path target = root.resolve(checksum);
        Files.copy(data, target, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public InputStream load(String checksum) throws IOException
    {
        return Files.newInputStream(root.resolve(checksum));
    }

    @Override
    public void delete(String checksum) throws IOException
    {
        Files.deleteIfExists(root.resolve(checksum));
    }
}
