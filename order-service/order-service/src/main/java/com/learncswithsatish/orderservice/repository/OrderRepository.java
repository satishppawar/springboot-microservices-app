package com.learncswithsatish.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learncswithsatish.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
