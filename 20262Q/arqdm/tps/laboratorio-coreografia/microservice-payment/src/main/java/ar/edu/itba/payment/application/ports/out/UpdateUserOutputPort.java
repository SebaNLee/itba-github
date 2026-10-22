package ar.edu.itba.payment.application.ports.out;

import ar.edu.itba.payment.application.core.domain.User;

public interface UpdateUserOutputPort {

    void execute(User user);
}
