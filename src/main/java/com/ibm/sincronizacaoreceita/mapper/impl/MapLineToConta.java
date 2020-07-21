package com.ibm.sincronizacaoreceita.mapper.impl;

import com.google.common.base.Splitter;
import com.ibm.sincronizacaoreceita.mapper.MapLineToObject;
import com.ibm.sincronizacaoreceita.model.Conta;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.Double.parseDouble;

@Component
public class MapLineToConta implements MapLineToObject<Conta> {

    @Override
    public Conta convert(String line) {
        List<String> ContaLine = Splitter.on(";").trimResults()
                                                 .omitEmptyStrings().splitToList(line);

        return Conta.builder().agencia(ContaLine.get(0))
                              .conta(ContaLine.get(1))
                              .saldo(ContaLine.get(2))
                              .status(ContaLine.get(3)).build();
    }
}
