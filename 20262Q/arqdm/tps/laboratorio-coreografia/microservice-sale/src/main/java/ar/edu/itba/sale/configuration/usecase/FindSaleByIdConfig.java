package ar.edu.itba.sale.configuration.usecase;

import ar.edu.itba.sale.adapters.FindSaleByIdAdapter;
import ar.edu.itba.sale.application.core.usecase.FindSaleByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindSaleByIdConfig {

    @Bean
    public FindSaleByIdUseCase findSaleByIdUseCase(final FindSaleByIdAdapter findSaleByIdAdapter) {
        return new FindSaleByIdUseCase(findSaleByIdAdapter);
    }
}
