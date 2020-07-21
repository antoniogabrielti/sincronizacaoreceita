package com.ibm.sincronizacaoreceita.repository;

import com.ibm.sincronizacaoreceita.model.Cabecalho;
import com.ibm.sincronizacaoreceita.model.Conta;

import java.util.List;

public interface LocalRepository {
    List<Conta> getContas();
    List<Cabecalho> getCabecalho();
    void setContas(List<Conta> contas);
    void setCabecalho(List<Cabecalho> cabecalho);
}
