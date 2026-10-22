package ar.edu.itba.payment.adapters.out.repository.mapper;

import ar.edu.itba.payment.adapters.out.repository.entity.UserEntity;
import ar.edu.itba.payment.application.core.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userEntityToUser(UserEntity userEntity);

    UserEntity userToUserEntity(User user);

}
