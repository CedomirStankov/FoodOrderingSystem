package com.nvp.domaci3.repositories;

import com.nvp.domaci3.model.Order;
import com.nvp.domaci3.model.Status;
import com.nvp.domaci3.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>{

  //List<Order> findOrdersByCreatedBy(Integer createdBy);
  @Query("SELECT p FROM Order p WHERE " +
    "(:status IS NULL OR p.status = :status) AND " +
    "(:dateFrom IS NULL OR p.createdAt >= :dateFrom) AND " +
    "(:dateTo IS NULL OR p.createdAt <= :dateTo) AND " +
    "(:createdBy IS NULL OR p.createdBy = :createdBy)")
  List<Order> findFilteredOrders(@Param("status") Status status,
                                 @Param("dateFrom") LocalDateTime dateFrom,
                                 @Param("dateTo") LocalDateTime dateTo,
                                 @Param("createdBy") Integer userId);
}
