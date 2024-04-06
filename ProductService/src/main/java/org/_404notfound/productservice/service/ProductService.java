package org._404notfound.productservice.service;

import org._404notfound.productservice.dto.ProductDTO;
import org._404notfound.productservice.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(ProductDTO productDTO);

    Product updateProduct(Long id, ProductDTO updatedProductDTO);

    void deleteProduct(Long id);
}
