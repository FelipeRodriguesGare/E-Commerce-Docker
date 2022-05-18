package br.com.letscode.produto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ProdutoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdutoApplication.class, args);
	}

}
