package com.nvp.domaci3.repositories;

import com.nvp.domaci3.model.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Integer> {
  public Page<ErrorMessage> findByUserId(PageRequest pageRequest, Integer userId);
}
