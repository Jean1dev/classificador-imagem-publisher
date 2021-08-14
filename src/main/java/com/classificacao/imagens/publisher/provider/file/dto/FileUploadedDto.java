package com.classificacao.imagens.publisher.provider.file.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileUploadedDto {

    private String nome;

    private String bucket;

    private String url;
}
