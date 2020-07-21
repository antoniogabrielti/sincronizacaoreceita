package com.ibm.sincronizacaoreceita.router;

import com.ibm.sincronizacaoreceita.configuration.InputConfiguration;
import com.ibm.sincronizacaoreceita.processor.ReceitaProcessor;
import com.ibm.sincronizacaoreceita.processor.CabecalhoProcessor;
import com.ibm.sincronizacaoreceita.processor.ContaProcessor;
import com.ibm.sincronizacaoreceita.processor.OutputProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartProcessRouter extends RouteBuilder {

    private InputConfiguration inputConfiguration;
    private CabecalhoProcessor cabecalhoProcessor;
    private ContaProcessor contaProcessor;
    private ReceitaProcessor receitaProcessor;
    private OutputProcessor outputProcessor;

    @Autowired
    public StartProcessRouter(InputConfiguration inputConfiguration,
                              CabecalhoProcessor cabecalhoProcessor,
                              ContaProcessor contaProcessor,
                              ReceitaProcessor receitaProcessor,
                              OutputProcessor outputProcessor) {
        this.inputConfiguration = inputConfiguration;
        this.cabecalhoProcessor = cabecalhoProcessor;
        this.contaProcessor = contaProcessor;
        this.receitaProcessor = receitaProcessor;
        this.outputProcessor = outputProcessor;
    }

    @Override
    public void configure() throws Exception {

        from(inputConfiguration.getInputPath()).log(".....Carregando Caminho do Arquivo")
                                               .startupOrder(1).to("direct:load").end();

        from("direct:load").log(".....Iniciando a quebra do cabecalho").validate(cabecalhoProcessor)
                                                                           .startupOrder(2).to("direct:conta").end();

        from("direct:conta").log(".....Iniciando a quebra das contas").validate(contaProcessor)
                                                                          .startupOrder(3).to("direct:bacen").end();

        from("direct:bacen").log(".....Enviando contas para a Receita").validate(receitaProcessor)
                                                                         .startupOrder(4).to("direct:output").end();

        from("direct:output").log(".....Gerando Arquivo com resultado")
                                 .validate(outputProcessor)
                                 .startupOrder(5).to("file://input/output?fileName=input-file-result.csv")
                                 .log(".....Arquivo Gerado com sucesso").end();

    }
}
