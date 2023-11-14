package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.UserDTO;
import com.makaia.flightReservation.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(UserDTO userDTO);
}
