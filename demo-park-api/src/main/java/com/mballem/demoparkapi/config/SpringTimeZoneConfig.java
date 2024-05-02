package com.mballem.demoparkapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

//anotação para dizer que essa class é uma class de configuração
@Configuration
public class SpringTimeZoneConfig {

    //Essa classe foi criada para dizer que o horário padrão desse projeto é de São Paulo
    @PostConstruct
    public void timezoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

}
