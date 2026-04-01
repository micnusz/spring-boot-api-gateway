package com.micnusz.sbag.Filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long start = System.currentTimeMillis();

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    long time = System.currentTimeMillis() - start;

                    var response = exchange.getResponse();

                    int status = response.getStatusCode() != null
                            ? response.getStatusCode().value()
                            : -1;

                    System.out.println(
                            exchange.getRequest().getMethod()
                                    + " " + exchange.getRequest().getURI()
                                    + " -> " + status
                                    + " (" + time + "ms)"
                    );
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}