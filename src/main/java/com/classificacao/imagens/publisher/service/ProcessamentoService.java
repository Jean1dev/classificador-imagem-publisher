package com.classificacao.imagens.publisher.service;

import com.classificacao.imagens.publisher.api.dto.CriarTarefaProcessamentoDto;
import com.classificacao.imagens.publisher.model.Processamento;
import com.classificacao.imagens.publisher.model.ProcessamentoRepository;
import com.classificacao.imagens.publisher.model.StatusProcessamento;
import com.classificacao.imagens.publisher.provider.file.StorageProvider;
import com.classificacao.imagens.publisher.provider.file.dto.FileUploadedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.classificacao.imagens.publisher.config.AMQPConstants.PROCESSAR_IMAGENS_QUEUE;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessamentoService {

    private final ProcessamentoRepository repository;

    private final StorageProvider storageProvider;

    private final RabbitTemplate rabbitTemplate;

    public String criarTarefaProcessamento(CriarTarefaProcessamentoDto dto) {
        FileUploadedDto upload = storageProvider.upload(dto.getFile());
        Processamento processamento = repository.save(Processamento.builder()
                .bucketName(upload.getBucket())
                .url(upload.getUrl())
                .keyFile(upload.getNome())
                .status(StatusProcessamento.PROCESSANDO)
                .build());

        rabbitTemplate.convertAndSend(PROCESSAR_IMAGENS_QUEUE, processamento);
        return processamento.getId();
    }
}
