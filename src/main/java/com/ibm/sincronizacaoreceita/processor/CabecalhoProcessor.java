package com.ibm.sincronizacaoreceita.processor;

import com.ibm.sincronizacaoreceita.mapper.MapLineToObject;
import com.ibm.sincronizacaoreceita.model.Cabecalho;
import com.ibm.sincronizacaoreceita.repository.LocalRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;

@Component
public class CabecalhoProcessor implements Predicate {

    private LocalRepository repository;
    private MapLineToObject<Cabecalho> mapLineToCabecalho;

    @Autowired
    public CabecalhoProcessor(LocalRepository repository, MapLineToObject<Cabecalho> mapLineToCabecalho) {
        this.repository = repository;
        this.mapLineToCabecalho = mapLineToCabecalho;
    }

    @Override
    public boolean matches(Exchange exchange) {
        File file =  exchange.getIn().getBody(File.class);

        String fileName = exchange.getIn().getHeader("CamelFileName").toString();

        if(idempotency(fileName)){
            try {
                List<Cabecalho> cabecalho = readCabecalho(file);
                cabecalho.add(Cabecalho.builder().nome("RESULTADO_RECEITA").build());
                repository.setCabecalho(cabecalho);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    private boolean idempotency(String fileName) {
        return fileName.equals("input-file.csv");
    }

    private List<Cabecalho> readCabecalho(File file) throws IOException {
        Optional<String> cabecalho = Files.lines(Paths.get(file.getAbsolutePath())).findFirst();
        return cabecalho.isPresent() ? cabecalhoToList(cabecalho.get()) : emptyList();
    }

    private List<Cabecalho> cabecalhoToList(String line){
       String[] cabecalhoLine = line.split(";");
       return stream(cabecalhoLine).map(mapLineToCabecalho::convert).collect(Collectors.toList());
    }
}
