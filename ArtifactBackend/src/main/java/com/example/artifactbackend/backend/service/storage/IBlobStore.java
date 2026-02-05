package com.example.artifactbackend.backend.service.storage;

import java.io.IOException;
import java.io.InputStream;

public interface IBlobStore {
    void store(String checksum, InputStream data) throws IOException;
    InputStream load(String checksum) throws IOException;
    void delete(String checksum) throws IOException;
}
