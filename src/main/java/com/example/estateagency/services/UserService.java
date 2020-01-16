package com.example.estateagency.services;

import com.example.estateagency.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
// Własne metody
    void save(User user);

    boolean isUniqueLogin(String login);

    Page<User> getAllUsers(Pageable pageable);

    void delete(Long id);

    boolean exists(Long id);

    User getById(Long id);

    User getUserByUsername(String username);

}
