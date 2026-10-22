package ar.edu.itba.sale.configuration.usecase;

import ar.edu.itba.sale.adapters.SaveSaleAdapter;
import ar.edu.itba.sale.adapters.SendCreatedSaleAdapter;
import ar.edu.itba.sale.application.core.usecase.CreateSaleUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateSaleConfig {
    @Bean
    public CreateSaleUseCase createSaleUseCase(SaveSaleAdapter saveSaleAdapter, SendCreatedSaleAdapter sendCreatedSaleAdapter) {
        return new CreateSaleUseCase(saveSaleAdapter, sendCreatedSaleAdapter);
    }
}
