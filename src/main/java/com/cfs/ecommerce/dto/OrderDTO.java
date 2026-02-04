package com.cfs.ecommerce.dto;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Long Id;

    private double totalAmount;

    private Date orderDate;

    private String userName;

    private String Status;

    private String email;

    private List<OrderItemDTO> orderItems;

    public OrderDTO(Long id, double totalAmount, Date orderDate, String userName, String status, String email, List<OrderItemDTO> orderItems) {
        Id = id;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.userName = userName;
        Status = status;
        this.email = email;
        this.orderItems = orderItems;
    }
    public OrderDTO(Long id, double totalAmount, Date orderDate, String status, List<OrderItemDTO> orderItems) {
        Id = id;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        Status = status;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
