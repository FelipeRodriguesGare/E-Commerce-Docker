package br.com.letscode.produto.annotation;

import br.com.letscode.produto.dto.UserResponse;
import br.com.letscode.produto.exception.Unauthorized;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticateAspect {
    //private final HttpServletRequest request;
    //private final ServerHttpRequest request;

    @Before("@annotation(Authenticate)")
    public void logExecutionTime() throws Unauthorized {

        WebClient client = WebClient.create("http://localhost:8083");
        Mono<UserResponse> userResponse = client.method(HttpMethod.GET)
                .uri("/user/authenticate")
                .header("Header-Name", "headervalue")
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.UNAUTHORIZED.value(),
                        response -> Mono.error(new Unauthorized("Unauthorized")) )
                .bodyToMono(UserResponse.class);
                //.block();
    }

}
