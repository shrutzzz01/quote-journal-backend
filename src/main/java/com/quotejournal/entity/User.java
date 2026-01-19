package com.quotejournal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Entity
@NoArgsConstructor
@Table(name="appUser")
public class  User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(length = 150, nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isVerified;
    @JsonIgnore
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Quote> quotes=new ArrayList<>();
    @Builder
    public User(Long userId, String name, String email, String password,  boolean isVerified, Role role, List<Quote> quotes) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
        this.role = role;
        this.quotes = quotes != null ? quotes : new ArrayList<>();
    }
}
