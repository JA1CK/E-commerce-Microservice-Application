package org._404notfound.orderservice.service;

import jakarta.transaction.Transactional;
import org._404notfound.orderservice.dto.OrderDTO;
import org._404notfound.orderservice.exceptions.OrderNotFoundException;
import org._404notfound.orderservice.exceptions.ProductNotFoundException;
import org._404notfound.orderservice.model.Order;
import org._404notfound.orderservice.model.OrderItem;
import org._404notfound.orderservice.model.Product;
import org._404notfound.orderservice.repository.OrderRepository;
import org._404notfound.orderservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(item.getQuantity());
                    return orderItem;
                })
                .collect(Collectors.toList());

        BigDecimal totalPrice = calculateTotalPrice(orderItems);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order); // Set the reference to the Order entity
        }

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with id " + id + " not found");
        }
        orderRepository.deleteById(id);
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            Product product = productRepository.findById(orderItem.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product with ID " + orderItem.getProduct().getId() + " not found"));

            if (product.getStock() < orderItem.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            totalPrice = totalPrice.add(itemPrice);

            product.setStock(product.getStock() - orderItem.getQuantity());
            productRepository.save(product);
        }
        return totalPrice;
    }
}
