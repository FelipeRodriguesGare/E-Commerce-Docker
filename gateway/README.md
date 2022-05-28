# Gateway

O objetivo do Spring Cloud Gateway é atuar como um API Gateway, de modo que podemos centralizar nossas chamadas,
redirecionar as chamadas para os serviços adequados e também atuar como um balanceador de carga, caso tenhamos mais de
uma instância rodando a mesma aplicação.

O Spring Cloud Gateway é baseado no Project Reactor.

# Configurar

Vamos adicionar as dependências. As duas primeiras são necessárias para o funcionamento do Gateway, a última é para
conseguir aproveitar o nosso Eureka Client para fazer o direcionamento e balanceamento de carga.

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-gateway</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>
		<!-- Cliente discovery do eureka -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>

É necessário também no arquivo de configuração escolher como vamos tratar os requests.

[Arquivo de propriedades](/src/main/resources/application.yml)

Testar:

* http://localhost:9995/eureka/hello
* http://localhost:9995/feign/hello

# Actuator

Caso usemos o actuator, o endpoint do health vai buscar a saúde de todas as aplicações que estão sendo redirecionadas.

Pelo endpoint /actuator/gateway/routes é possível obter também todas as rotas que estão sendo tratadas.

# Segurança

Adicionar dependência

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

> @EnableWebFluxSecurity

## Keycloak

> docker run --name myKeyCloak -p 8089:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -d jboss/keycloak:12.0.4

# Bibliografia

* https://cloud.spring.io/spring-cloud-gateway/reference/html/
* https://dzone.com/articles/quick-guide-to-microservices-with-spring-boot-20-e
* https://migueldoctor.medium.com/run-keycloak-locally-using-docker-and-secure-your-apps-in-3-steps-f6c554427e54