package com.appsdeveloperblog.photoapp.api.gateway;

import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    public AuthorizationHeaderFilter(){
        super(Config.class);
    }

    @Autowired
    Environment env;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange,chain) -> {
            ServerHttpRequest request=exchange.getRequest();
            if(!request.getHeaders().containsKey("Authorization")){
                return onError(exchange,"No Authorization hader", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader=request.getHeaders().get("Authorization").get(0);
            String jwt=authorizationHeader.replace("Bearer","");

            if(!isJwtvalid(jwt)){
                return onError(exchange,"Jwt token is not valid", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus ){
        ServerHttpResponse response=exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean isJwtvalid(String jwt){
        boolean returnValue=true;
        String subject=null;
        try {
            subject = Jwts.parser()
                    .setSigningKey(env.getProperty("token.sectet"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        }catch(Exception e){
    returnValue=false;
        }
        if(subject==null || subject.isEmpty()){
            returnValue=false;
        }
        return returnValue;
    }

    public static class Config{
     //put configuration properties here
    }
}
