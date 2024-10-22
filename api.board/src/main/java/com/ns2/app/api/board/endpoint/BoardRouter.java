package com.ns2.app.api.board.endpoint;

import com.ns2.app.api.board.handler.BoardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class BoardRouter {
    @Bean
    public RouterFunction<ServerResponse> routes(BoardHandler handler) {
        return RouterFunctions
                .route(GET("/board/{id}").and(accept(APPLICATION_JSON)), handler::findById)
                .andRoute(POST("/board").and(accept(APPLICATION_JSON)), handler::save)
                ;
    }
}
