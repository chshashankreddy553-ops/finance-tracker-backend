package com.finance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double amount;

    private String title;

    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Transaction() {}

    public Long getId() { return id; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public Double getAmount() { return amount; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public User getUser() { return user; }

    public void setId(Long v) { id = v; }
    public void setType(String v) { type = v; }
    public void setCategory(String v) { category = v; }
    public void setAmount(Double v) { amount = v; }
    public void setTitle(String v) { title = v; }
    public void setDescription(String v) { description = v; }
    public void setDate(LocalDate v) { date = v; }
    public void setUser(User v) { user = v; }
}