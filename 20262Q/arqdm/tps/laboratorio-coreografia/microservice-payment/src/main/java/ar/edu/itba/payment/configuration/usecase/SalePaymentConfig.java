package ar.edu.itba.payment.configuration.usecase;

import ar.edu.itba.payment.adapters.out.SavePaymentAdapter;
import ar.edu.itba.payment.adapters.out.SendValidatedPaymentAdapter;
import ar.edu.itba.payment.adapters.out.UpdateUserAdapter;
import ar.edu.itba.payment.application.core.usecase.FindUserByIdUseCase;
import ar.edu.itba.payment.application.core.usecase.SalePaymentUseCase;
import ar.edu.itba.payment.application.ports.out.SendFailedPaymentOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalePaymentConfig {

    @Bean
    public SalePaymentUseCase salePaymentUseCase(final FindUserByIdUseCase findUserByIdUseCase,
                                                 final UpdateUserAdapter updateUserAdapter,
                                                 final SavePaymentAdapter savePaymentAdapter,
                                                 final SendValidatedPaymentAdapter sendValidatedPaymentAdapter,
                                                 final SendFailedPaymentOutputPort sendFailedPaymentOutputPort) {
        return new SalePaymentUseCase(findUserByIdUseCase, updateUserAdapter, savePaymentAdapter,
                sendValidatedPaymentAdapter, sendFailedPaymentOutputPort);
    }
}
