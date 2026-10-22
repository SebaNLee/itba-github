package ar.edu.itba.payment.application.ports.out;

import ar.edu.itba.payment.application.core.domain.User;

import java.util.Optional;

public interface FindUserByIdOutputPort {

    Optional<User> execute(Long id);
}
