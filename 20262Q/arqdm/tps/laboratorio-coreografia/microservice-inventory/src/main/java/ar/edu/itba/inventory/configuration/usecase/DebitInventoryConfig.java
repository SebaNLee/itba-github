package ar.edu.itba.inventory.configuration.usecase;

import ar.edu.itba.inventory.adapters.out.SendDebitInventoryAdapter;
import ar.edu.itba.inventory.adapters.out.SendFailedInventoryAdapter;
import ar.edu.itba.inventory.adapters.out.UpdateInventoryAdapter;
import ar.edu.itba.inventory.application.core.usecase.DebitInventoryUseCase;
import ar.edu.itba.inventory.application.core.usecase.FindInventoryByProductIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebitInventoryConfig {

    @Bean
    public DebitInventoryUseCase debitInventoryUseCase(final FindInventoryByProductIdUseCase findInventoryByProductIdUseCase,
                                                       final UpdateInventoryAdapter updateInventoryAdapter,
                                                       final SendDebitInventoryAdapter sendUpdateInventoryAdapter,
                                                       final SendFailedInventoryAdapter sendFailedInventoryAdapter) {

        return new DebitInventoryUseCase(findInventoryByProductIdUseCase, updateInventoryAdapter,
                sendUpdateInventoryAdapter, sendFailedInventoryAdapter);
    }
}
