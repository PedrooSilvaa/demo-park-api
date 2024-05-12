package com.mballem.demoparkapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class SpringTimezoneConfig {

    @PostConstruct
    public void timezoneConfig() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}

/*
@Configuration: Esta anotação marca a classe como uma classe de configuração Spring,
indicando que ela contém configurações para a aplicação.

@PostConstruct: Esta anotação é usada para marcar um método que deve ser executado logo após a
construção da instância da classe. Neste caso, o método timezoneConfig() será executado após a
construção da instância da classe.

timezoneConfig(): Este é o método que define o fuso horário padrão da aplicação para "America/Sao_Paulo".
Ele é chamado automaticamente após a construção da instância da classe devido à anotação @PostConstruct.

TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo")): Este é o código que define o fuso horário
padrão da JVM para "America/Sao_Paulo". Isso afetará o comportamento de data e hora em toda a aplicação.

Portanto, essa classe de configuração Spring garante que o fuso horário padrão da aplicação seja
configurado corretamente durante a inicialização, evitando assim possíveis problemas relacionados à
manipulação de datas e horas.
*/