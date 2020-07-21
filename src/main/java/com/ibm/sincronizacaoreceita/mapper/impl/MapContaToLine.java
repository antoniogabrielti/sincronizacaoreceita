package com.ibm.sincronizacaoreceita.mapper.impl;

import com.ibm.sincronizacaoreceita.mapper.MapObjectToLine;
import com.ibm.sincronizacaoreceita.model.Conta;
import org.springframework.stereotype.Component;

@Component
public class MapContaToLine implements MapObjectToLine<Conta> {

    @Override
    public String convert(Conta conta) {
        return conta.getAgencia()+";"+
               conta.getConta()+";"+
               conta.getSaldo()+";"+
               conta.getStatus()+";"+
               formatResultBacen(conta.getResultado());
    }

    private String formatResultBacen(Boolean result){
        return result ? "Enviado" : "Falhou";
    }
}
