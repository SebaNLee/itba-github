package ar.edu.itba.payment.application.ports.out;

import ar.edu.itba.payment.application.core.domain.Payment;

public interface SavePaymentOutputPort {

    void execute(Payment payment);
}
