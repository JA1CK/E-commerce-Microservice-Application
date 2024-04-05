package org._404notfound.adminservice.service;

import org._404notfound.adminservice.model.Order;
import org._404notfound.adminservice.model.Product;

import java.util.List;

public interface AdminService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(Product product);

    void deleteProduct(Long id);

    Product updateProduct(Long id, Product updatedProduct);

    List<Order> viewAllOrders();
}
