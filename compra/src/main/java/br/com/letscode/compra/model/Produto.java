package br.com.letscode.compra.model;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private String codigo;
    private String nome;
    private Double preco;
    private Integer qtde_disponivel;

}
