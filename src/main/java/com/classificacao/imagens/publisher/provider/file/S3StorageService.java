package com.classificacao.imagens.publisher.provider.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.classificacao.imagens.publisher.provider.file.dto.FileUploadedDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Qualifier("aws-s3")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class S3StorageService implements StorageProvider {

    private static final Logger LOG = LoggerFactory.getLogger(S3StorageService.class);

    private final AmazonS3 s3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @SneakyThrows
    @Override
    public FileUploadedDto upload(MultipartFile multipartFile) {
        String keyName = UUID.randomUUID().toString() + ".jpg";
        File file = new File(keyName);

        Path path = Paths.get(file.getAbsolutePath());
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        LOG.info("arquivo criado no disco");

        s3.putObject(bucket, keyName, file);
        s3.setObjectAcl(bucket, keyName, CannedAccessControlList.PublicRead);

        LOG.info("upload para o s3 concluido");

        file.delete();

        return FileUploadedDto.builder()
                .nome(keyName)
                .bucket(bucket)
                .url(s3.getUrl(bucket, keyName).toURI().toString())
                .build();
    }
}
