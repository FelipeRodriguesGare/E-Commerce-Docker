package br.com.letscode.compra.service;

import br.com.letscode.compra.model.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("produto-service")
public interface ProdutoAPI {

    @GetMapping("produto")
    List<Produto> getAll();

    @GetMapping("produto/{id}")
    Produto getProduct(@PathVariable String id);

    @PutMapping("produto")
    void updateQuantity(@RequestBody Map.Entry<String, Integer> produtos);

}
