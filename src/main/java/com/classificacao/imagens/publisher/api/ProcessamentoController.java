package com.classificacao.imagens.publisher.api;

import com.classificacao.imagens.publisher.api.dto.CriarTarefaProcessamentoDto;
import com.classificacao.imagens.publisher.model.Processamento;
import com.classificacao.imagens.publisher.model.ProcessamentoRepository;
import com.classificacao.imagens.publisher.service.ProcessamentoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(path = ProcessamentoController.PATH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessamentoController {

    public static final String PATH = "criar-processamento";

    private final ProcessamentoService service;

    private final ProcessamentoRepository repository;

    @ApiOperation(value = "criar uma tarefa de processamento de imagem")
    @ApiResponse(code = 202, message = "tarefa criada, situação em processamento")
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map processar(@RequestParam("file") MultipartFile file) {
        String idProcessamento = service.criarTarefaProcessamento(CriarTarefaProcessamentoDto.builder()
                .file(file)
                .build());

        return Map.of("idProcessamento", idProcessamento);
    }

    @ApiOperation(value = "Retorna os dados da tarefa de processamento")
    @GetMapping(path = "{id}")
    public Processamento getOne(@PathVariable("id") String id) {
        return repository.findById(id).orElseThrow();
    }
}
