package com.lidosta.Deikstras_Lidosta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
public class DeikstrasLidostaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeikstrasLidostaApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer mvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://*")
                        .allowedMethods("PUT", "DELETE", "GET", "POST");

            }
        };
    }
}
