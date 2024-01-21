package com.sellerbuyer.sellerbuyer.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Seller-Buyer Management System API")
                        .version("1.0.0")
                        .description("This API handles the interactions between sellers and buyers within a marketplace.")
                        .termsOfService("http://sellerbuyer.com/terms")
                        .contact(new Contact()
                                .name("Seller-Buyer Support")
                                .email("support@sellerbuyer.com")
                                .url("http://sellerbuyerdemo.com/contact"))
                        .license(new License()
                                .name("Use under XYZ License")
                                .url("http://sellerbuyerdemo.com/license"))
                ).components(new Components()
                        .addSecuritySchemes("basicAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }
}
