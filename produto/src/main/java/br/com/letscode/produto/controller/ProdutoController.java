package br.com.letscode.produto.controller;

import br.com.letscode.produto.dto.ProdutoRequest;
import br.com.letscode.produto.dto.ProdutoResponse;
import br.com.letscode.produto.exception.BadRequest;
import br.com.letscode.produto.exception.NotFound;
import br.com.letscode.produto.model.Produto;
import br.com.letscode.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProdutoResponse> getAll(Produto produto) throws NotFound {
        return produtoService.listTodos(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProdutoResponse> createProduct(@RequestBody @Valid ProdutoRequest produtoRequest) {
        return produtoService.createProduct(produtoRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProdutoResponse> getProduct(@PathVariable String id) throws NotFound {
        return produtoService.findByCodigo(id)
                .switchIfEmpty(Mono.error(new NotFound("Produto não encontrado")));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateQuantity(@RequestBody Map.Entry<String, Integer> produtos) throws BadRequest, NotFound {
        produtoService.updateQuantity(produtos);
    }
}
