package org._404notfound.productservice.service;

import jakarta.transaction.Transactional;
import org._404notfound.productservice.dto.ProductDTO;
import org._404notfound.productservice.exceptions.ImageNotFoundException;
import org._404notfound.productservice.exceptions.ProductNotFoundException;
import org._404notfound.productservice.model.Image;
import org._404notfound.productservice.model.Product;
import org._404notfound.productservice.repository.ImageRepository;
import org._404notfound.productservice.repository.ProductRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ImageRepository imageRepository, ProductRepository productRepository) {
        this.imageRepository = imageRepository;
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
}
