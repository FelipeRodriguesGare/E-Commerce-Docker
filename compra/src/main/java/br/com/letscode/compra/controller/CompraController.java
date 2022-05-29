package br.com.letscode.compra.controller;

import br.com.letscode.compra.dto.CompraRequest;
import br.com.letscode.compra.dto.KafkaDTO;
import br.com.letscode.compra.exceptions.BadRequest;
import br.com.letscode.compra.exceptions.NotFound;
import br.com.letscode.compra.model.Compra;
import br.com.letscode.compra.service.CompraService;
import br.com.letscode.compra.service.ProdutoAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compra")
public class CompraController {

    private final CompraService compraService;

    @GetMapping()
    public ResponseEntity<Object> listCompras(Compra compra) throws NotFound {
        return ResponseEntity.ok(compraService.listCompra(compra));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void createProduct(@RequestBody @Valid CompraRequest compraRequest, BindingResult bindingResult, HttpServletRequest request) throws BadRequest {
        if(bindingResult.hasErrors()){
            throw new BadRequest("O campo " + bindingResult.getFieldError().getField() + " deve ser preenchido.");
        }
        KafkaDTO kafkaDTO = new KafkaDTO(request.getHeader("Authorization"), compraRequest);
        compraService.enviaKafka(kafkaDTO);
    }

}
