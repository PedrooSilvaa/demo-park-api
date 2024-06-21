package com.mballem.demoparkapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    // Configuração para definir a documentação OpenAPI/Swagger da API
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                // Configura os componentes da OpenAPI, como esquemas de segurança
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                // Configura informações gerais sobre a API
                .info(
                        new Info()
                                .title("REST API - Spring Park") // Título da API
                                .description("API para gestão de estacionamento de veículos") // Descrição da API
                                .version("v1") // Versão da API
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")) // Informações da licença
                                .contact(new Contact().name("Marcio Ballem").email("marcio@spring-park.com")) // Informações de contato do desenvolvedor
                );
    }

    // Configuração do esquema de segurança
    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insira um bearer token valido para prosseguir") // Descrição do esquema de segurança
                .type(SecurityScheme.Type.HTTP) // Tipo de esquema de segurança (HTTP)
                .in(SecurityScheme.In.HEADER) // Localização do token no request (cabeçalho)
                .scheme("bearer") // Nome do esquema de segurança (Bearer Token)
                .bearerFormat("JWT") // Formato esperado do token (JWT)
                .name("security"); // Nome do esquema de segurança usado nas operações da API
    }
}
