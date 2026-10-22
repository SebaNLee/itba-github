package ar.edu.itba.payment.application.core.usecase;

import ar.edu.itba.payment.application.core.domain.User;
import ar.edu.itba.payment.application.ports.in.FindUserByIdInputPort;
import ar.edu.itba.payment.application.ports.out.FindUserByIdOutputPort;

public class FindUserByIdUseCase implements FindUserByIdInputPort {

    private final FindUserByIdOutputPort findUserByIdOutputPort;

    public FindUserByIdUseCase(final FindUserByIdOutputPort findUserByIdOutputPort) {
        this.findUserByIdOutputPort = findUserByIdOutputPort;
    }

    @Override
    public User execute(Long id) {
        return this.findUserByIdOutputPort.execute(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
