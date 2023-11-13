package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.UserDTO;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.UserMapper;
import com.makaia.flightReservation.model.User;
import com.makaia.flightReservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO setRole(Integer userId, Integer roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Airport not found with ID: " + userId));
        user.setRoleId(roleId);
        try {
            return userMapper.toDto(userRepository.save(user));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while updating user: " + e.getMessage());
        }
    }
}
