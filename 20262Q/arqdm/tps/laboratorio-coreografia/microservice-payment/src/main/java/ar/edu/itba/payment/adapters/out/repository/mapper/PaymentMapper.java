package ar.edu.itba.payment.adapters.out.repository.mapper;

import ar.edu.itba.payment.adapters.out.repository.entity.PaymentEntity;
import ar.edu.itba.payment.application.core.domain.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentEntity paymentToPaymentEntity(Payment payment);
}
