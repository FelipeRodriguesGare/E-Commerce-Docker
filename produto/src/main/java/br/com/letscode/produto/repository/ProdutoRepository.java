package br.com.letscode.produto.repository;


import br.com.letscode.produto.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends ReactiveMongoRepository<Produto, String> {

    Mono<Produto> findFirstByCodigo(String codigo);

}
