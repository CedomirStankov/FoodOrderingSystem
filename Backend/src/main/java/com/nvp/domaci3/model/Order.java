package com.nvp.domaci3.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="porudzbina")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private Status status=Status.ORDERED;

  @Column
  private Integer createdBy;

  @Column
  private Boolean active;

  @Column
  private String items;

  @CreationTimestamp
  @Column
  private LocalDateTime createdAt;

  @Version
  private Integer version;

}
