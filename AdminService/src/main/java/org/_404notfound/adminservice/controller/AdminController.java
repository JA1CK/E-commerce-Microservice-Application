package org._404notfound.adminservice.controller;

import org._404notfound.adminservice.dto.ProductDTO;
import org._404notfound.adminservice.model.Order;
import org._404notfound.adminservice.model.Product;
import org._404notfound.adminservice.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = adminService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = adminService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = adminService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO updatedProduct) {
        Product product = adminService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> viewAllOrders() {
        List<Order> orders = adminService.viewAllOrders();
        return ResponseEntity.ok(orders);
    }
}
