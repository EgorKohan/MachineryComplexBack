package com.bsuir.services;

import com.bsuir.models.User;

public interface UserService {

    User save(User user);

    User findByEmail(String email);

}
