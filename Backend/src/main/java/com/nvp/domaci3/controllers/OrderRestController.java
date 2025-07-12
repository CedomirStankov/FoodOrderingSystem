package com.nvp.domaci3.controllers;

import com.nvp.domaci3.model.Order;
import com.nvp.domaci3.model.ScheduledOrder;
import com.nvp.domaci3.model.Status;
import com.nvp.domaci3.model.User;
import com.nvp.domaci3.scheduler.OrderStatusScheduler;
import com.nvp.domaci3.services.OrderService;
import com.nvp.domaci3.services.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

  private final OrderService orderService;

  private final OrderStatusScheduler orderStatusScheduler;

  public OrderRestController(OrderService orderService, OrderStatusScheduler orderStatusScheduler){
    this.orderService=orderService;
    this.orderStatusScheduler=orderStatusScheduler;
  }

//  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
//  public List<Order> getAllOrders(){
//    return orderService.findAll();
//  }
//
//  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//  public List<Order> findOrdersByUserId(@PathVariable Integer id){
//    return orderService.findOrdersByUserId(id);
//  }

  @PreAuthorize("hasAuthority('SEARCH_ORDER')")
  @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Order> getFilteredOrders(@RequestParam(required = false) Status status,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo,
                               @RequestParam(required = false) Integer userId){
    LocalDateTime fromDateTime = dateFrom != null ? dateFrom.atStartOfDay() : null;
    LocalDateTime toDateTime = dateTo != null ? dateTo.atTime(LocalTime.MAX) : null;
    return orderService.findFilteredOrders(status, fromDateTime, toDateTime, userId);
  }

  @PreAuthorize("hasAuthority('PLACE_ORDER')")
  @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Order> addOrder(@RequestBody Order order){
    Order savedOrder = orderService.save(order);

    orderStatusScheduler.scheduleOrderStatusChange(savedOrder.getId(), savedOrder.getStatus());

    return ResponseEntity.ok(savedOrder);
  }

  @PreAuthorize("hasAuthority('CANCEL_ORDER')")
  @PatchMapping(value = "/cancel/{id}")
  public ResponseEntity<String> cancelOrder(@PathVariable Integer id){
    boolean success =  orderService.cancelOrder(id);
    if(success){

      return ResponseEntity.ok("Order canceled successfully");
    }else{
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cannot be canceled");
    }
  }

  @PreAuthorize("hasAuthority('SCHEDULE_ORDER')")
  @PostMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> schedule(@RequestBody ScheduledOrder scheduledOrder){
    orderService.scheduleOrder(scheduledOrder);

    return ResponseEntity.ok("Order scheduled!");
  }
}
