package com.nvp.domaci3.controllers;

import com.nvp.domaci3.model.ErrorMessage;
import com.nvp.domaci3.model.User;
import com.nvp.domaci3.scheduler.OrderStatusScheduler;
import com.nvp.domaci3.services.ErrorMessageService;
import com.nvp.domaci3.services.OrderService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/errorMessages")
public class ErrorMessageRestController {

  private final ErrorMessageService errorMessageService;

  public ErrorMessageRestController(ErrorMessageService errorMessageService){
    this.errorMessageService=errorMessageService;
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<ErrorMessage> getAllErrorMessages(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
    return errorMessageService.findAllPaged(page,size);
  }

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<ErrorMessage> findErrorMessagesByUserId(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @PathVariable Integer userId){
    return errorMessageService.findByUserId(page,size,userId);
  }

}
