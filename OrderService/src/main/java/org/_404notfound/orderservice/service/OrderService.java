package org._404notfound.orderservice.service;

import org._404notfound.orderservice.dto.OrderDTO;
import org._404notfound.orderservice.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id);

    Order createOrder(OrderDTO orderDTO);

    void deleteOrder(Long id);
}
