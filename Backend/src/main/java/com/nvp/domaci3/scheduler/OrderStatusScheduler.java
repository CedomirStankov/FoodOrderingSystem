package com.nvp.domaci3.scheduler;

import com.nvp.domaci3.model.ErrorMessage;
import com.nvp.domaci3.model.Order;
import com.nvp.domaci3.model.Status;
import com.nvp.domaci3.repositories.ErrorMessageRepository;
import com.nvp.domaci3.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OrderStatusScheduler {

  private final TaskScheduler taskScheduler;
  private final OrderRepository orderRepository;
  private final ErrorMessageRepository errorMessageRepository;

  private int preparingCount=0;
  private int indeliveryCount=0;

  @Autowired
  public OrderStatusScheduler(TaskScheduler taskScheduler, OrderRepository orderRepository, ErrorMessageRepository errorMessageRepository) {
    this.taskScheduler = taskScheduler;
    this.orderRepository = orderRepository;
    this.errorMessageRepository = errorMessageRepository;
  }

  public void scheduleOrderStatusChange(Integer orderId, Status currentStatus) {
    Status nextStatus = getNextStatus(currentStatus);
    if (nextStatus == null) {
      return;
    }

    long delay = getDelayForStatus(nextStatus) * 1000L;

    taskScheduler.schedule(() -> {
      updateOrderStatus(orderId, nextStatus);

      scheduleOrderStatusChange(orderId, nextStatus);
    }, new Date(System.currentTimeMillis() + delay));
  }

  private Status getNextStatus(Status currentStatus) {
    switch (currentStatus) {
      case ORDERED:
        return Status.PREPARING;
      case PREPARING:
        return Status.IN_DELIVERY;
      case IN_DELIVERY:
        return Status.DELIVERED;
      default:
        return null;
    }
  }

  private int getDelayForStatus(Status status) {
    switch (status) {
      case PREPARING:
        return 10 + (int) (Math.random() * 5);
      case IN_DELIVERY:
        return 15 + (int) (Math.random() * 5);
      case DELIVERED:
        return 20 + (int) (Math.random() * 5);
      default:
        return 0;
    }
  }

  private void updateOrderStatus(Integer orderId, Status newStatus) {
    Optional<Order> orderOpt = orderRepository.findById(orderId);
    if (orderOpt.isPresent()) {
      Order order = orderOpt.get();
      if (canUpdateStatus(order.getStatus(), newStatus)) {
        if(order.getStatus()==Status.ORDERED && newStatus==Status.PREPARING){
          preparingCount++;
          System.out.println("sada je preparing ovde, preparingCount: " + preparingCount);
        }
        if(order.getStatus()==Status.PREPARING && newStatus==Status.IN_DELIVERY){
          indeliveryCount++;
          System.out.println("sada je in delivery ovde, indeliveryCount: " + indeliveryCount);
        }
        if(order.getStatus()==Status.PREPARING && newStatus==Status.IN_DELIVERY){
          preparingCount--;
          System.out.println("sada je in delivery ovde, preparingCount: " + preparingCount);
        }
        if(order.getStatus()==Status.IN_DELIVERY && newStatus==Status.DELIVERED){
          indeliveryCount--;
          System.out.println("sada je delivered ovde, indeliveryCount: " + indeliveryCount);
        }
        if(preparingCount>3){
          preparingCount--;
          //System.out.println("PREKORACENJE");
          order.setStatus(Status.CANCELED);
          order.setActive(false);
          //System.out.println("SIMULACIJA GRESKE: Order "+ order.getId() + " je izazvao gresku");
          ErrorMessage errorMessage = new ErrorMessage();
          errorMessage.setOrderId(order.getId());
          errorMessage.setUserId(order.getCreatedBy());
          errorMessage.setMessage("Order with id "+ order.getId() + " failed, because there were already 3 orders in PREPARING stage");
          errorMessageRepository.save(errorMessage);
          orderRepository.save(order);
          System.out.println("Order " + orderId + " updated to " + Status.CANCELED);
        }else if(indeliveryCount>3){
          indeliveryCount--;
          //System.out.println("PREKORACENJE");
          order.setStatus(Status.CANCELED);
          order.setActive(false);
          //System.out.println("SIMULACIJA GRESKE: Order "+ order.getId() + " je izazvao gresku");
          ErrorMessage errorMessage = new ErrorMessage();
          errorMessage.setOrderId(order.getId());
          errorMessage.setUserId(order.getCreatedBy());
          errorMessage.setMessage("Order with id "+ order.getId() + " failed, because there were already 3 orders in IN_DELIVERY stage");
          errorMessageRepository.save(errorMessage);
          orderRepository.save(order);
          System.out.println("Order " + orderId + " updated to " + Status.CANCELED);
        }else{
          order.setStatus(newStatus);
          if(order.getStatus()==Status.DELIVERED){
            order.setActive(false);
          }
          orderRepository.save(order);
          System.out.println("Order " + orderId + " updated to " + newStatus);
        }
      }
    }
  }

  private boolean canUpdateStatus(Status currentStatus, Status newStatus) {
    return getNextStatus(currentStatus) == newStatus;
  }
}
