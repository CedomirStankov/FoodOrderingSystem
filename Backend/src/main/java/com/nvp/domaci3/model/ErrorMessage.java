package com.nvp.domaci3.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="errormessage")
public class ErrorMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @CreationTimestamp
  @Column
  private LocalDateTime createdAt;

  @Column
  private String message;

  @Column
  private Integer orderId;

  @Column
  private Integer userId;

  @Version
  private Integer version;

}
