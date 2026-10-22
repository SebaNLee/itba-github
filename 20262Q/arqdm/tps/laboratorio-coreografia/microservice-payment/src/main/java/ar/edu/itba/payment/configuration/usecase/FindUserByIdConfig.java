package ar.edu.itba.payment.configuration.usecase;

import ar.edu.itba.payment.adapters.out.FindUserByIdAdapter;
import ar.edu.itba.payment.application.core.usecase.FindUserByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindUserByIdConfig {

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(final FindUserByIdAdapter findUserByIdAdapter) {
        return new FindUserByIdUseCase(findUserByIdAdapter);
    }
}
