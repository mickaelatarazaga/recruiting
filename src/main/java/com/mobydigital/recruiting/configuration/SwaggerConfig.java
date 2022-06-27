package com.mobydigital.recruiting.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(@Value("${project.version}") String appVersion) {
        OpenAPI openApi = new OpenAPI();
        openApi.info(
                new Info()
                        .title("Recruiting")
                        .version(appVersion)
                        .description(" ")
        );

        return openApi;
    }
}

