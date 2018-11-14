package com.foxminded.airline.domain.service.impl;

import com.foxminded.airline.domain.entity.Role;
import com.foxminded.airline.domain.entity.User;
import com.foxminded.airline.domain.service.UserService;
import com.foxminded.airline.dto.UserDTO;
import com.foxminded.airline.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String cryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public User save(User user) {
        user.setRole(Role.USER.getRole());
        return userRepository.save(user);
    }

    @Override
    public void editPassportData(User user, UserDTO userDTO) {
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setPatronym(userDTO.getPatronym());
        user.setPassportNumber(userDTO.getPassportNumber());
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }


}
