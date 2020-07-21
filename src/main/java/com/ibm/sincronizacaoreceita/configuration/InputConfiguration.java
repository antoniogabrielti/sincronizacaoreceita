package com.ibm.sincronizacaoreceita.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class InputConfiguration {

    @Value("${input.path}")
    private String inputPath;

    public String getInputPath() {
        return inputPath;
    }
}
