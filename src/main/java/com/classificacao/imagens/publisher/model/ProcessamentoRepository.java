package com.classificacao.imagens.publisher.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessamentoRepository extends MongoRepository<Processamento, String> {
}
