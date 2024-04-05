package org._404notfound.adminservice.repository;

import org._404notfound.adminservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
