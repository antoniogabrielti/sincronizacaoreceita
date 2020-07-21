package com.ibm.sincronizacaoreceita.repository.impl;

import com.ibm.sincronizacaoreceita.model.Cabecalho;
import com.ibm.sincronizacaoreceita.model.Conta;
import com.ibm.sincronizacaoreceita.repository.LocalRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocalRepositoryImpl implements LocalRepository {
    private List<Conta> contas;
    private List<Cabecalho> cabecalho;

    @Override
    public List<Conta> getContas() {
        return this.contas;
    }

    @Override
    public List<Cabecalho> getCabecalho() {
        return this.cabecalho;
    }

    @Override
    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    @Override
    public void setCabecalho(List<Cabecalho> cabecalho) {
        this.cabecalho = cabecalho;
    }
}
