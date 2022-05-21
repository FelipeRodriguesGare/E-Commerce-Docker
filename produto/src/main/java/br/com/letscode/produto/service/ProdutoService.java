package br.com.letscode.produto.service;

import br.com.letscode.produto.dto.ProdutoRequest;
import br.com.letscode.produto.dto.ProdutoResponse;
import br.com.letscode.produto.exception.BadRequest;
import br.com.letscode.produto.exception.NotFound;
import br.com.letscode.produto.model.Produto;
import br.com.letscode.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public Flux<ProdutoResponse> listTodos(Produto produto) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example example = Example.of(produto, matcher);
        Pageable pageable = PageRequest.of(0,5);
        Flux<Produto> produtoList = produtoRepository.findAll(example)
                .switchIfEmpty(Mono.error(new NotFound("Produto não encontrado")));
        Flux<ProdutoResponse> produtoResponses = produtoList.map(p -> new ProdutoResponse(p));
        return produtoResponses;
    }

    public Mono<ProdutoResponse> findByCodigo(String codigo){
        return produtoRepository.findFirstByCodigo(codigo).flatMap(ProdutoResponse::convert);
    }

    public Mono<ProdutoResponse> createProduct(ProdutoRequest produtoRequest) {
        String productCode = this.createProductCode();
        Mono<ProdutoResponse> produtoVerificado = findByCodigo(productCode);

//        while (produtoVerificado!=null){
//            productCode = this.createProductCode();
//            produtoVerificado = findByCodigo(productCode);
//        }

        Produto produto = new Produto();
        produto.setCodigo(productCode);
        produto.setNome(produtoRequest.getNome());
        produto.setPreco(produtoRequest.getPreco());
        produto.setQtde_disponivel(produtoRequest.getQtdeDisponivel());

        Mono<Produto> produto1 = produtoRepository.save(produto);
        return produto1.flatMap(p -> ProdutoResponse.convert(p));

    }

    public String createProductCode(){
        Random ra = new Random();
        Character prefixo = (char) (ra.nextInt(26) + 'A');
        Integer nAleatorio = ra.nextInt(999);
        String sufixo;
        if(nAleatorio<=9){
            sufixo = "00" + Integer.toString(nAleatorio);
        }else if(nAleatorio<=99){
            sufixo = "0" + Integer.toString(nAleatorio);
        }else{
            sufixo = Integer.toString(nAleatorio);
        }
        return  prefixo + sufixo;
    }

    public void updateQuantity(Map.Entry<String, Integer> produtoRecebido) throws BadRequest, NotFound {
        produtoRepository.findFirstByCodigo(produtoRecebido.getKey()).subscribe(p -> {
            p.setQtde_disponivel(p.getQtde_disponivel() - produtoRecebido.getValue());
            produtoRepository.save(p).subscribe();
        });
    }
}