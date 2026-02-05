package com.example.artifactbackend.backend.utils;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.DigestInputStream;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.util.HexFormat;

public class HashUtils
{
    public static String computeSha256(InputStream data) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            try (DigestInputStream dis = new DigestInputStream(data, digest)) {
                byte[] buffer = new byte[8192];
                while (dis.read(buffer) != -1) {
                }
            }

            byte[] hash = digest.digest();
            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
