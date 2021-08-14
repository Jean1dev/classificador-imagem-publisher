package com.classificacao.imagens.publisher.provider.file;

import com.classificacao.imagens.publisher.provider.file.dto.FileUploadedDto;
import org.springframework.web.multipart.MultipartFile;

public interface StorageProvider {

    FileUploadedDto upload(MultipartFile multipartFile);
}
