package com.nvp.domaci3.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Boolean can_read_users;

    @Column
    private Boolean can_create_users;

    @Column
    private Boolean can_update_users;

    @Column
    private Boolean can_delete_users;

    @Column
    private Boolean can_search_order;

    @Column
    private Boolean can_place_order;

    @Column
    private Boolean can_cancel_order;

    @Column
    private Boolean can_track_order;

    @Column
    private Boolean can_schedule_order;

    @Version
    private Integer version;

}
