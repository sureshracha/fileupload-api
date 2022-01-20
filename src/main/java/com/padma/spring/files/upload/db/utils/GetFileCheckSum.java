package com.padma.spring.files.upload.db.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetFileCheckSum {

    public String checksum(File input) throws NoSuchAlgorithmException {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                input.toString().getBytes(StandardCharsets.UTF_8));

        return  hex(hash);

    }
    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }
}