package com.nvp.domaci3.services;

import com.nvp.domaci3.model.ErrorMessage;
import com.nvp.domaci3.model.Order;
import com.nvp.domaci3.repositories.ErrorMessageRepository;
import com.nvp.domaci3.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ErrorMessageService implements IService<ErrorMessage,Integer>{

  private final ErrorMessageRepository errorMessageRepository;

  @Autowired
  public ErrorMessageService(@Qualifier("errorMessageRepository") ErrorMessageRepository errorMessageRepository) {
    this.errorMessageRepository = errorMessageRepository;
  }

  @Override
  public <S extends ErrorMessage> S save(S var1) {
    return null;
  }

  @Override
  public Optional<ErrorMessage> findById(Integer var1) {
    return Optional.empty();
  }

  @Override
  public List<ErrorMessage> findAll() {
    return null;
  }

  public Page<ErrorMessage> findAllPaged(Integer page, Integer size) {
    return errorMessageRepository.findAll(PageRequest.of(page,size,Sort.by("createdAt").descending()));
  }

  @Override
  public void deleteById(Integer var1) {

  }

  @Override
  public <S extends ErrorMessage> S update(Integer var1, S var2) {
    return null;
  }

  public Page<ErrorMessage> findByUserId(Integer page, Integer size, Integer userId) {
    return errorMessageRepository.findByUserId(PageRequest.of(page,size,Sort.by("createdAt").descending()), userId);
  }
}
