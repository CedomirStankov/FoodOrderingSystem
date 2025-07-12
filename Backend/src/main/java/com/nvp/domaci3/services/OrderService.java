package com.nvp.domaci3.services;

import com.nvp.domaci3.model.Order;
import com.nvp.domaci3.model.ScheduledOrder;
import com.nvp.domaci3.model.Status;
import com.nvp.domaci3.repositories.OrderRepository;
import com.nvp.domaci3.scheduler.OrderStatusScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IService<Order,Integer>{

  private final OrderRepository orderRepository;
  private final TaskScheduler taskScheduler;

  private final OrderStatusScheduler orderStatusScheduler;

  @Autowired
  public OrderService(@Qualifier("orderRepository") OrderRepository orderRepository, TaskScheduler taskScheduler, OrderStatusScheduler orderStatusScheduler) {
    this.orderRepository = orderRepository;
    this.taskScheduler = taskScheduler;
    this.orderStatusScheduler = orderStatusScheduler;
  }
  @Override
  public <S extends Order> S save(S order) {
    return orderRepository.save(order);
  }

  @Override
  public Optional<Order> findById(Integer var1) {
    return Optional.empty();
  }

  @Override
  public List<Order> findAll() {
    return (List<Order>) orderRepository.findAll();
  }

  @Override
  public void deleteById(Integer var1) {

  }

  @Override
  public <S extends Order> S update(Integer var1, S var2) {
    return null;
  }

  public boolean cancelOrder(Integer id){
    Optional<Order> order = orderRepository.findById(id);
    if(order.isPresent()){
      if(order.get().getStatus()==Status.ORDERED){
        order.get().setStatus(Status.CANCELED);
        order.get().setActive(false);
        orderRepository.save(order.get());
        return true;
      }
    }
    return false;
  }

//  public List<Order> findOrdersByUserId(Integer id) {
//    return orderRepository.findOrdersByCreatedBy(id);
//  }

  public List<Order> findFilteredOrders(Status status, LocalDateTime dateFrom, LocalDateTime dateTo, Integer userId){
    return orderRepository.findFilteredOrders(status, dateFrom, dateTo, userId);
  }

  public void scheduleOrder(ScheduledOrder scheduledOrder) {
    LocalDateTime now = LocalDateTime.now();

    if (scheduledOrder.getDateTime().isBefore(now)) {
      System.out.println("Scheduled time is in the past, skipping execution.");
      return;
    }

    Date scheduledDate = Date.from(scheduledOrder.getDateTime().atZone(ZoneId.systemDefault()).toInstant());

    taskScheduler.schedule(() -> {
      System.out.println("Executing scheduled order: ");
      System.out.println("Food: " + scheduledOrder.getFood());
      System.out.println("DateTime: " + scheduledOrder.getDateTime());
      System.out.println("CreatedBy: " + scheduledOrder.getCreatedBy());

      Order order = new Order();
      order.setItems(scheduledOrder.getFood());
      order.setCreatedBy(scheduledOrder.getCreatedBy());
      order.setStatus(Status.ORDERED);
      order.setActive(true);
      System.out.println("sada cuvam ovde order");
      Order savedOrder = save(order);
      orderStatusScheduler.scheduleOrderStatusChange(savedOrder.getId(), savedOrder.getStatus());

    }, scheduledDate);

    System.out.println("Order scheduled for: " + scheduledOrder.getDateTime());
  }

}
