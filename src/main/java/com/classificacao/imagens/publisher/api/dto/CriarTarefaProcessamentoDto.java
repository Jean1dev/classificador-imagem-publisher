package com.classificacao.imagens.publisher.api.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class CriarTarefaProcessamentoDto {

    private MultipartFile file;
}
