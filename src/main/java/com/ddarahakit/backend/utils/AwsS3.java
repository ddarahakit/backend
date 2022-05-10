package com.ddarahakit.backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AwsS3 {

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    @Value("${cloud.aws.region.static}")
    public String region;

    @Value("${cloud.aws.credentials.accessKey}")
    public String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    public String secretKey;

    private S3Client client;


    public String upload(String url,MultipartFile multipartFile)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {

        Region regionObject = Region.of(region);
        client = S3Client.builder()
                .region(regionObject)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();

        //실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
        String originalName = multipartFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        String uuid = UUID.randomUUID().toString();
        String folderPath = url + File.separator + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
        String saveName = folderPath + File.separator + uuid +"_" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(saveName)
                //.acl("public-read")
                .contentType("image/*")
                .build();

        RequestBody requestBody = RequestBody.fromInputStream(
                multipartFile.getInputStream(), multipartFile.getSize()
        );

        client.putObject(putObjectRequest, requestBody);


        return "https://ddarahakit-s3.s3.ap-northeast-2.amazonaws.com/"+saveName;
    }
}
