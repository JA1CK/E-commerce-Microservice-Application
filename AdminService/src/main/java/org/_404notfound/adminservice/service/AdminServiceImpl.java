package org._404notfound.adminservice.service;

import jakarta.transaction.Transactional;
import org._404notfound.adminservice.dto.ProductDTO;
import org._404notfound.adminservice.exceptions.ImageNotFoundException;
import org._404notfound.adminservice.exceptions.ProductNotFoundException;
import org._404notfound.adminservice.model.Image;
import org._404notfound.adminservice.model.Order;
import org._404notfound.adminservice.model.Product;
import org._404notfound.adminservice.repository.ImageRepository;
import org._404notfound.adminservice.repository.OrderRepository;
import org._404notfound.adminservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final ImageRepository imageRepository;

    public AdminServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, ImageRepository imageRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
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
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        String imageIdString = productDTO.getImageId();
        Image image = imageRepository.findById(Long.parseLong(imageIdString))
                .orElseThrow(() -> new ImageNotFoundException("Image with id " + imageIdString + " not found"));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setImage(image);

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
    public Product updateProduct(Long id, ProductDTO updatedProductDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with id " + id + " not found"));

        existingProduct.setName(updatedProductDTO.getName());
        existingProduct.setDescription(updatedProductDTO.getDescription());
        existingProduct.setPrice(updatedProductDTO.getPrice());
        existingProduct.setStock(updatedProductDTO.getStock());

        // Check if the updated product DTO has a non-null image ID
        if (updatedProductDTO.getImageId() != null) {
            String imageIdString = updatedProductDTO.getImageId();
            Image image = imageRepository.findById(Long.parseLong(imageIdString))
                    .orElseThrow(() -> new ImageNotFoundException("Image with id " + imageIdString + " not found"));
            existingProduct.setImage(image);
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public List<Order> viewAllOrders() {
        return orderRepository.findAll();
    }
}
