package org._404notfound.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("products", r -> r.path("/products/**")
                        .uri("http://localhost:8081/products"))
                .route("orders", r -> r.path("/orders/**")
                        .uri("http://localhost:8082/orders"))
                .route("admin", r -> r.path("/admin/**")
                        .uri("http://localhost:8083/admin"))
                .route("images", r -> r.path("/images/**")
                        .uri("http://localhost:8084/images"))
                .build();
    }
}
