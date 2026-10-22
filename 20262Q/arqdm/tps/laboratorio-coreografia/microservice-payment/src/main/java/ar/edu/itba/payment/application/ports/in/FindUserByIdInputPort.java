package ar.edu.itba.payment.application.ports.in;

import ar.edu.itba.payment.application.core.domain.User;

public interface FindUserByIdInputPort {

    User execute(Long id);
}
