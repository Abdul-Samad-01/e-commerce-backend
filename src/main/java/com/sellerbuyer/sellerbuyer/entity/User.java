package com.sellerbuyer.sellerbuyer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uuid;
    @Column(unique = true, nullable = false)
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String countryCode;
    private boolean isSuperUser;
    private boolean isStaff;
    private boolean isActive;
    private Date lastLogin;
    private Date lastLogout;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
