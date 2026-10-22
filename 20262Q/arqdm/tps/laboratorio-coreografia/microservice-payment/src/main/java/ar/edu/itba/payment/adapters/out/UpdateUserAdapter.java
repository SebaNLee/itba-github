package ar.edu.itba.payment.adapters.out;

import ar.edu.itba.payment.adapters.out.repository.UserRepository;
import ar.edu.itba.payment.adapters.out.repository.mapper.UserMapper;
import ar.edu.itba.payment.application.core.domain.User;
import ar.edu.itba.payment.application.ports.out.UpdateUserOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserAdapter implements UpdateUserOutputPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void execute(final User user) {
        final var userEntity = userMapper.userToUserEntity(user);
        this.userRepository.saveAndFlush(userEntity);
    }

}
