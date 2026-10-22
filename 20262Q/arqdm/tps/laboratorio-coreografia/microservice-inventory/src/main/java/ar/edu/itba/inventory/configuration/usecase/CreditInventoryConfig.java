package ar.edu.itba.inventory.configuration.usecase;

import ar.edu.itba.inventory.adapters.out.SendCreditInventoryAdapter;
import ar.edu.itba.inventory.application.core.usecase.CreditInventoryUseCase;
import ar.edu.itba.inventory.application.core.usecase.FindInventoryByProductIdUseCase;
import ar.edu.itba.inventory.application.ports.out.UpdateInventoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreditInventoryConfig {

    @Bean
    public CreditInventoryUseCase creditInventoryUseCase(final FindInventoryByProductIdUseCase findInventoryByProductIdUseCase,
                                                         final UpdateInventoryOutputPort updateInventoryOutputPort,
                                                         final SendCreditInventoryAdapter sendCreditInventoryAdapter) {

        return new CreditInventoryUseCase(findInventoryByProductIdUseCase, updateInventoryOutputPort, sendCreditInventoryAdapter);
    }

}
