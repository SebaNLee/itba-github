package ar.edu.itba.payment.adapters.out;

import ar.edu.itba.payment.adapters.out.repository.UserRepository;
import ar.edu.itba.payment.adapters.out.repository.mapper.UserMapper;
import ar.edu.itba.payment.application.core.domain.User;
import ar.edu.itba.payment.application.ports.out.FindUserByIdOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindUserByIdAdapter implements FindUserByIdOutputPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> execute(final Long id) {
        final var userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::userEntityToUser);
    }
}
