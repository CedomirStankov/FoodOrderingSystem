package com.nvp.domaci3.repositories;

import com.nvp.domaci3.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, CustomUserRepository {
    public User findByEmail(String email);
}
