package org._404notfound.adminservice.repository;

import org._404notfound.adminservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
