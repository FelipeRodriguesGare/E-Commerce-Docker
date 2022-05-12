package br.com.letscode.produto.annotation;

import br.com.letscode.produto.dto.UserResponse;
import br.com.letscode.produto.exception.Unauthorized;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticateAspect {

    private final HttpServletRequest request;

    @Value("${auth.endereco}")
    private String HOST;

    @Value("${auth.porta}")
    private String PORT;

    @Before("@annotation(Authenticate)")
    public void logExecutionTime() throws Unauthorized {
        String requestHeader = request.getHeader("Authorization");

        WebClient client = WebClient.create(this.HOST+":"+this.PORT);
        UserResponse userResponse = client.method(HttpMethod.GET)
                .uri("/user/authenticate")
                .header("Authorization", requestHeader)
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.UNAUTHORIZED.value(),
                        response -> Mono.error(new Unauthorized("Unauthorized")) )
                .bodyToMono(UserResponse.class)
                .block();
    }

}
