package com.tamu.dmeditorbackend.service;
;

import com.tamu.dmeditorbackend.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User payload);

    User login(User payload);

    Boolean validateToken(String token);

    Long vendorId (String token);

    String vendorEmail(String token);

    User updateUser (User payload, Long id);

    List<User> getAllUser ();

    void deleteUser (Long id);
}
