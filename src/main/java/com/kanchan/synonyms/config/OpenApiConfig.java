package com.kanchan.synonyms.config;

import java.util.Optional;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Snonyms Register")
                                 .description("Snonyms Register")
                                 .version("v1"));
    }
    
    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            Optional<PreAuthorize> preAuthorizeAnnotation = Optional.ofNullable(handlerMethod.getMethodAnnotation(PreAuthorize.class));
            StringBuilder sb = new StringBuilder();
            if (preAuthorizeAnnotation.isPresent()) {
                sb.append("This api requires **")
                        .append((preAuthorizeAnnotation.get()).value().replaceAll("hasAuthority|\\(|\\)|\\'", ""))
                        .append("** permission.");
            } else {
                sb.append("This api is **public**");
            }
            sb.append("<br /><br />");
           // sb.append(operation.getDescription());
            operation.setDescription(sb.toString());
            return operation;
        };
    }
}