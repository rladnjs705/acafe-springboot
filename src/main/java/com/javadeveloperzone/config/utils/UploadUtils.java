package com.javadeveloperzone.config.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class UploadUtils {

    @Value("${gcp.storage.bucket-name}")
    private static String BUCKET = "sungbok";

    @Value("classpath:/google/universal-valve-379504-da6ef330f216.json")
    private Resource resourceFile;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(resourceFile.getInputStream());
        StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
        Storage storage = options.getService();

        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(BUCKET, fileName))
                .setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo, file.getInputStream().readAllBytes());
        String path = blob.getBlobId().getBucket()+"/"+blob.getBlobId().getName();
        return path;
    }

    public byte[] downloadFile(String fileName) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Blob blob = storage.get(BUCKET, fileName);
        return blob.getContent();
    }
}
