package com.ibm.sincronizacaoreceita.processor;

import com.ibm.sincronizacaoreceita.mapper.MapLineToObject;
import com.ibm.sincronizacaoreceita.model.Conta;
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
import java.util.stream.Collectors;

@Component
public class ContaProcessor implements Predicate {

    private MapLineToObject<Conta> mapLineToConta;
    private LocalRepository repository;

    @Autowired
    public ContaProcessor(MapLineToObject<Conta> mapLineToConta, LocalRepository repository) {
        this.mapLineToConta = mapLineToConta;
        this.repository = repository;
    }

    @Override
    public boolean matches(Exchange exchange) {
        File file =  exchange.getIn().getBody(File.class);

        String fileName = exchange.getIn().getHeader("CamelFileName").toString();

        if(idempotency(fileName)){

            try {
                List<Conta> contas = readContas(file);
                repository.setContas(contas);
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

    private List<Conta> readContas(File file) throws IOException {
        return Files
                .lines(Paths.get(file.getAbsolutePath())).skip(1)
                .map(line -> mapLineToConta.convert(line)).collect(Collectors.toList());
    }
}
