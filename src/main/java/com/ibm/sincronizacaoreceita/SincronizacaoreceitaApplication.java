package com.ibm.sincronizacaoreceita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(value={"com.ibm.sincronizacaoreceita"})
public class SincronizacaoreceitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoreceitaApplication.class,args);
	}
}