package com.appsdeveloperblog.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiletersConfiguration {

    final Logger logger= LoggerFactory.getLogger(GlobalFiletersConfiguration.class);

    @Bean
    @Order(1)
    public GlobalFilter secondPreFilter(){
        return (exchange,chain) -> {
            logger.info("my second pre filter is executed");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("my second post filter was executed");
            }));
        };
    }

    @Bean
    @Order(2)
    public GlobalFilter thirdPreFilter(){
        return (exchange,chain) -> {
            logger.info("my third pre filter is executed");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("my third post filter was executed");
            }));
        };
    }

    @Bean
    @Order(3)
    public GlobalFilter fourthPreFilter(){
        return (exchange,chain) -> {
            logger.info("my fourth pre filter is executed");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("my fourth post filter was executed");
            }));
        };
    }
}
