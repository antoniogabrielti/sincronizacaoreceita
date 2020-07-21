package com.ibm.sincronizacaoreceita.processor;

import com.ibm.sincronizacaoreceita.mapper.MapObjectToLine;
import com.ibm.sincronizacaoreceita.model.Cabecalho;
import com.ibm.sincronizacaoreceita.model.Conta;
import com.ibm.sincronizacaoreceita.repository.LocalRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OutputProcessor implements Predicate {

    private LocalRepository repository;
    private MapObjectToLine<Conta> mapContaToLine;

    @Autowired
    public OutputProcessor(LocalRepository repository, MapObjectToLine<Conta> mapContaToLine) {
        this.repository = repository;
        this.mapContaToLine = mapContaToLine;
    }


    @Override
    public boolean matches(Exchange exchange) {
        String cabecalhoLine = repository.getCabecalho().stream().map(Cabecalho::getNome)
                                                                 .collect(Collectors.joining(";"));

        List<String> contasLine = repository.getContas().stream().map(mapContaToLine::convert)
                                                                 .collect(Collectors.toList());

        List<String> resultProcess = new ArrayList<>();
        resultProcess.add(cabecalhoLine);
        resultProcess.addAll(contasLine);
        exchange.getOut().setBody(String.join("\n", resultProcess));

        return true;
    }
}
