package ar.edu.itba.sale.configuration.usecase;

import ar.edu.itba.sale.adapters.SaveSaleAdapter;
import ar.edu.itba.sale.application.core.usecase.FinalizeSaleUseCase;
import ar.edu.itba.sale.application.core.usecase.FindSaleByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinalizeSaleConfig {

    @Bean
    public FinalizeSaleUseCase finalizeSaleUseCase(final FindSaleByIdUseCase findSaleByIdUseCase,
                                                   final SaveSaleAdapter saveSaleAdapter) {
        return new FinalizeSaleUseCase(findSaleByIdUseCase, saveSaleAdapter);
    }
}
