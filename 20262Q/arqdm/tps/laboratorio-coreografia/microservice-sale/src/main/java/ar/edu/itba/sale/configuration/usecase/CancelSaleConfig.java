package ar.edu.itba.sale.configuration.usecase;

import ar.edu.itba.sale.adapters.SaveSaleAdapter;
import ar.edu.itba.sale.application.core.usecase.CancelSaleUseCase;
import ar.edu.itba.sale.application.core.usecase.FindSaleByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelSaleConfig {

    @Bean
    public CancelSaleUseCase cancelSaleUseCase(final FindSaleByIdUseCase findSaleByIdUseCase,
                                               final SaveSaleAdapter saveSaleAdapter) {
        return new CancelSaleUseCase(findSaleByIdUseCase, saveSaleAdapter);
    }
}
