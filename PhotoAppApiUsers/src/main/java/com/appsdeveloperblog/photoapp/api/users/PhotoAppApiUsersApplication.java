package com.appsdeveloperblog.photoapp.api.users;

import com.appsdeveloperblog.photoapp.api.users.shared.FeignErrorDecoder;
import com.sun.javafx.util.Logging;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class PhotoAppApiUsersApplication {

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(PhotoAppApiUsersApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    Logger.Level feignLoggerLover() {
        return Logger.Level.FULL;
    }


    @Bean
    @Profile("production")
    public String createProductionBean() {
        System.out.println("Production bean created " + environment.getProperty("myapplication.environment"));
        return "Production Bean";
    }

    @Bean
    @Profile("!production")
    public String createNotProductionBean() {
        System.out.println("Not production bean created " + environment.getProperty("myapplication.environment"));
        return "Not production Bean";
    }

    @Bean
    @Profile("default")
    public String createDevelopmentBean() {
        System.out.println("Development bean created " + environment.getProperty("myapplication.environment"));
        return "Development Bean";
    }



		/*
		@Bean
		public FeignErrorDecoder getFeignErrorDecoder()
		{
			return new FeignErrorDecoder();
		}

		 */
}
