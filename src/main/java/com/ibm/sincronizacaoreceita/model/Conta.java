package com.ibm.sincronizacaoreceita.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Conta {
    private String agencia;
    private String conta;
    private String saldo;
    private String status;
    private Boolean resultado;
}
