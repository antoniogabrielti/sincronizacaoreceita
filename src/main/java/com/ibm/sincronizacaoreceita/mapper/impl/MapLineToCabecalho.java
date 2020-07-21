package com.ibm.sincronizacaoreceita.mapper.impl;

import com.ibm.sincronizacaoreceita.mapper.MapLineToObject;
import com.ibm.sincronizacaoreceita.model.Cabecalho;
import org.springframework.stereotype.Component;

@Component
public class MapLineToCabecalho implements MapLineToObject<Cabecalho> {

    @Override
    public Cabecalho convert(String nome) {
        return Cabecalho.builder().nome(nome).build();
    }
}
