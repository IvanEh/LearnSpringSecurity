package com.gmail.at.ivanehreshi.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    boolean save(UserDetails user);
}
