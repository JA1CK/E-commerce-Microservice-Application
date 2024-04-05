package org._404notfound.orderservice.repository;

import org._404notfound.orderservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
