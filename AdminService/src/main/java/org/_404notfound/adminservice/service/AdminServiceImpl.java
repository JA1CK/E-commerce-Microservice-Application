package org._404notfound.adminservice.service;

import org._404notfound.adminservice.exceptions.ProductNotFoundException;
import org._404notfound.adminservice.model.Order;
import org._404notfound.adminservice.model.Product;
import org._404notfound.adminservice.repository.OrderRepository;
import org._404notfound.adminservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public AdminServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with id " + id + " not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setImageId(updatedProduct.getImageId());

        return productRepository.save(existingProduct);
    }

    @Override
    public List<Order> viewAllOrders() {
        return orderRepository.findAll();
    }
}
