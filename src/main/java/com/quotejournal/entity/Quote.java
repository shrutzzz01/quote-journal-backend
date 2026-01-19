package com.quotejournal.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="quotes")
public class Quote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteId;
    @Column(nullable = false)
    private String content;
    private Tag tag;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    private boolean isPublic;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

}
