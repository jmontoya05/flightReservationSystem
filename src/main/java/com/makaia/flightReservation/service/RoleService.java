package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.RoleDTO;
import com.makaia.flightReservation.dto.RoleRequestDTO;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.mapper.RoleMapper;
import com.makaia.flightReservation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleDTO saveRole(RoleRequestDTO roleRequestDTO) {
        try {
            return roleMapper.toDto(roleRepository.save(roleMapper.requestToRole(roleRequestDTO)));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving role: " + e.getMessage());
        }
    }

    public List<RoleDTO> getRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }
}
