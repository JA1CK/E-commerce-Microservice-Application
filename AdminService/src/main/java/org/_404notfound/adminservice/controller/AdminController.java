package org._404notfound.adminservice.controller;

import org._404notfound.adminservice.model.Order;
import org._404notfound.adminservice.model.Product;
import org._404notfound.adminservice.service.AdminService;
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
    public List<Product> getAllProducts() {
        return adminService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return adminService.getProductById(id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return adminService.createProduct(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return adminService.updateProduct(id, updatedProduct);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> viewAllOrders() {
        List<Order> orders = adminService.viewAllOrders();
        return ResponseEntity.ok(orders);
    }
}
