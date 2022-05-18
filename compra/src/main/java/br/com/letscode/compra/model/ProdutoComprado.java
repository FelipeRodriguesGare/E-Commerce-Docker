package br.com.letscode.compra.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoComprado {

    private String codigo;
    private String nome;
    private Double preco;
    private Integer qtd_comprada;
    private String statusProduto;

    public ProdutoComprado(String codigo, String nome, Double preco, Integer qtd_comprada) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.qtd_comprada = qtd_comprada;
    }
}
