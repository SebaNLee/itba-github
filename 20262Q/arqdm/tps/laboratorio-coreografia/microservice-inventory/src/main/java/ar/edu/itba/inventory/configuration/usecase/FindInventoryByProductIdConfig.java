package ar.edu.itba.inventory.configuration.usecase;

import ar.edu.itba.inventory.adapters.out.FindInventoryByProductIdAdapter;
import ar.edu.itba.inventory.application.core.usecase.FindInventoryByProductIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindInventoryByProductIdConfig {

    @Bean
    public FindInventoryByProductIdUseCase findInventoryByProductIdUseCase(final FindInventoryByProductIdAdapter findInventoryByProductIdAdapter) {
        return new FindInventoryByProductIdUseCase(findInventoryByProductIdAdapter);
    }
}
