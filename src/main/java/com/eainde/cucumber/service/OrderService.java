package com.eainde.cucumber.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eainde.cucumber.entity.Order;
import com.eainde.cucumber.repository.OrderRepository;

import com.eainde.cucumber.entity.Order;
import com.eainde.cucumber.repository.OrderRepository;

@Service
public class OrderService {
  private final OrderRepository repository;

  OrderService(final OrderRepository repository) {
    this.repository = repository;
  }

  public Order findById(int id) {
    return repository.findById(id);
  }

  public List<Order> findAll() {
    return repository.findAll();
  }

  public void add(Order order) {
    repository.add(order);
  }

  public Order update(Order order) {
    return repository.update(order);
  }
}
