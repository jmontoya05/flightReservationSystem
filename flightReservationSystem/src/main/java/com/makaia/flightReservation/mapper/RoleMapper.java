package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.RoleDTO;
import com.makaia.flightReservation.dto.RoleRequestDTO;
import com.makaia.flightReservation.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDto(Role role);
    Role toRole(RoleDTO roleDTO);

    @Mapping(target = "roleId", ignore = true)
    Role requestToRole(RoleRequestDTO roleRequestDTO);
}
