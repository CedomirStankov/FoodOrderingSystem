package com.nvp.domaci3.repositories;

import com.nvp.domaci3.model.User;

public interface CustomUserRepository {
    User update(Integer id, User user);
}