package com.pm.apigateway.filter;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(@LoadBalanced WebClient.Builder webClientBuilder) {
        // Use Eureka logical service ID for user-service
        this.webClient = webClientBuilder.baseUrl("lb://user-service").build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    .uri("/user/validate") // Calls validate endpoint on user-service
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(response -> chain.filter(exchange)) // Forward if valid
                    .onErrorResume(ex -> {

                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        String errorMessage = "{ \"error\": \"Invalid or expired token\" }";
                        byte[] bytes = errorMessage.getBytes(StandardCharsets.UTF_8);
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

                        return exchange.getResponse().writeWith(Mono.just(buffer));
                    });
        };
    }
}
