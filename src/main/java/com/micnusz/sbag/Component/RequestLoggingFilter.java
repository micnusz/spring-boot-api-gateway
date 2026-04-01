package com.micnusz.sbag.Component;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {

        long start = System.currentTimeMillis();
        long id = counter.incrementAndGet();

        String method = serverWebExchange.getRequest().getMethod().name();
        String path = serverWebExchange.getRequest().getURI().getPath();

        return gatewayFilterChain.filter(serverWebExchange)
                .doFinally(signal -> {
                    long time = System.currentTimeMillis() - start;

                    int status = serverWebExchange.getResponse().getStatusCode() != null
                            ? serverWebExchange.getResponse().getStatusCode().value()
                            : 0;

                    System.out.println(
                            "[" + id + "] " + method + " " + path + " : " + status + " (" + time + "ms)"
                    );
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}