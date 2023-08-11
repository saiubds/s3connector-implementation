package com.connection.s3coms;

import io.awspring.cloud.s3.S3Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@RestController
public class Controller {

    @Value("s3://com.ubds.swift.s.sai-ult-test-bucket-cli/samplefile.txt")
    private Resource s3SampleFile;

    @GetMapping("/data")
    public ResponseEntity<String> getData()
    {
        try(Reader reader = new InputStreamReader(s3SampleFile.getInputStream(), StandardCharsets.UTF_8)){
            return ResponseEntity.ok(FileCopyUtils.copyToString(reader));
        } catch (Exception e)
        {
            return ResponseEntity.internalServerError().body(
                    "could not fetch content"
            );
        }
    }

    @PostMapping("/data")
    public ResponseEntity<?> putData(@RequestBody String data) throws Exception
    {
        try (OutputStream outputStream = ((S3Resource) s3SampleFile).getOutputStream())
        {
            outputStream.write(data.getBytes(StandardCharsets.UTF_8));
        }
        return ResponseEntity.ok(data);
    }
}
