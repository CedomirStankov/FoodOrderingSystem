package com.nvp.domaci3.repositories;

import com.nvp.domaci3.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User update(Integer id, User user) {
        User existingUser = entityManager.find(User.class, id);
        if (existingUser != null) {
            if(user.getName()!=null){
                existingUser.setName(user.getName());
            }
            if(user.getLastName()!=null){
                existingUser.setLastName(user.getLastName());
            }
            if(user.getEmail()!=null){
                existingUser.setEmail(user.getEmail());
            }
            if(user.getPassword()!=null){
                existingUser.setPassword(user.getPassword());
            }
            if(user.getCan_create_users()!=null){
                existingUser.setCan_create_users(user.getCan_create_users());
            }
            if(user.getCan_update_users()!=null){
                existingUser.setCan_update_users(user.getCan_update_users());
            }
            if(user.getCan_read_users()!=null){
                existingUser.setCan_read_users(user.getCan_read_users());
            }
            if(user.getCan_delete_users()!=null){
                existingUser.setCan_delete_users(user.getCan_delete_users());
            }
            if(user.getCan_search_order()!=null){
              existingUser.setCan_search_order(user.getCan_search_order());
            }
            if(user.getCan_place_order()!=null){
              existingUser.setCan_place_order(user.getCan_place_order());
            }
            if(user.getCan_cancel_order()!=null){
              existingUser.setCan_cancel_order(user.getCan_cancel_order());
            }
            if(user.getCan_track_order()!=null){
              existingUser.setCan_track_order(user.getCan_track_order());
            }
            if(user.getCan_schedule_order()!=null){
              existingUser.setCan_schedule_order(user.getCan_schedule_order());
            }



            entityManager.merge(existingUser);
        }
        return existingUser;
    }
}
