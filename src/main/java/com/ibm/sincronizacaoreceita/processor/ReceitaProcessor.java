package com.ibm.sincronizacaoreceita.processor;

import com.ibm.sincronizacaoreceita.model.Conta;
import com.ibm.sincronizacaoreceita.repository.LocalRepository;
import com.ibm.sincronizacaoreceita.service.ReceitaService;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Double.parseDouble;

@Component
public class ReceitaProcessor implements Predicate {

    private ReceitaService receitaService;
    private LocalRepository repository;

    @Autowired
    public ReceitaProcessor(ReceitaService receitaService, LocalRepository repository) {
        this.receitaService = receitaService;
        this.repository = repository;
    }

    @Override
    public boolean matches(Exchange exchange) {
        List<Conta> contas = repository.getContas();
        repository.setContas(contas.stream().map(this::sendReceita).collect(Collectors.toList()));
        return true;
    }

    private Conta sendReceita(Conta conta){
        try {
            boolean response = receitaService.atualizarConta(conta.getAgencia(),
                                                             conta.getConta().replaceAll("-",""),
                                                             parseDouble(conta.getSaldo().replaceAll(",",".")),
                                                             conta.getStatus());
            conta.setResultado(response);
            return conta;
        } catch (InterruptedException | RuntimeException e) {
            System.err.println("Ocorreu um erro ao enviar a Receita a "+conta.toString());
            conta.setResultado(FALSE);
            return conta;
        }
    }
}
