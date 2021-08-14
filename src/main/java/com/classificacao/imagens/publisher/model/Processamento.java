package com.classificacao.imagens.publisher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("processamento")
@Builder
@Getter
@AllArgsConstructor
public class Processamento {

    @Id
    private String id;

    private String bucketName;

    private String url;

    private String keyFile;

    private StatusProcessamento status;
}
