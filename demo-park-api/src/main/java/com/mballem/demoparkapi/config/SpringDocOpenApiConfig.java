package com.mballem.demoparkapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - Spring Park")
                                .description("API para gestão de estacionamento de veículos")
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Marcio Ballem").email("marcio@spring-park.com"))
                );
    }
}
/*
@Configuration: Esta anotação marca a classe como uma classe de configuração Spring, indicando que
ela contém configurações para a aplicação.

@Bean: Esta anotação é usada para marcar um método que produz um bean a ser gerenciado pelo contêiner
Spring. Neste caso, o método openAPI() produz um bean do tipo OpenAPI.

public OpenAPI openAPI(): Este é o método que cria e configura um objeto OpenAPI, que representa a
documentação da API OpenAPI (anteriormente conhecida como Swagger).

return new OpenAPI()...: Aqui, estamos inicializando um objeto OpenAPI e definindo suas propriedades,
como título, descrição, versão, informações de licença e informações de contato.

Informações da API (Info): Estamos configurando informações sobre a API, como título, descrição e versão,
usando o objeto Info.

Informações de Licença (License): Estamos definindo as informações de licença da API como "Apache 2.0" e
o URL da licença.

Contato (Contact): Estamos definindo informações de contato para a API, incluindo nome e e-mail do contato.

Portanto, essa classe de configuração Spring é responsável por configurar e fornecer informações para
a documentação da API usando o Springdoc OpenAPI, o que é útil para descrever e documentar os endpoints
da API.
*/