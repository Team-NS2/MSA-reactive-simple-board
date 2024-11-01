package com.ns2.app.auth.server.endpoint;

import com.ns2.app.auth.server.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class AuthRouter {

    @Bean
    public RouterFunction<ServerResponse> route(AuthHandler handler) {
        return RouterFunctions.route(RequestPredicates.POST("/auth/signup").and(accept(MediaType.APPLICATION_JSON)), handler::signup)
                .andRoute(RequestPredicates.POST("/auth/login").and(accept(MediaType.APPLICATION_JSON)), handler::login);
    }
}
