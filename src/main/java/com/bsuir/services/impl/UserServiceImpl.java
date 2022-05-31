package com.bsuir.services.impl;

import com.bsuir.models.Role;
import com.bsuir.models.User;
import com.bsuir.repositories.RoleRepository;
import com.bsuir.repositories.UserRepository;
import com.bsuir.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User save(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email " + user.getEmail() + " is already exist");
        user.setRoles(getSavedRoles(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    private Set<Role> getSavedRoles(User user) {
        return user.getRoles().stream()
                .map(role -> {
                    Optional<Role> existedRole = roleRepository.findByRoleType(role.getRoleType());
                    return existedRole.orElse(role);
                }).collect(Collectors.toSet());
    }

}
