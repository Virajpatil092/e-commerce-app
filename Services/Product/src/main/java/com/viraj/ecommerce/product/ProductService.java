package com.viraj.ecommerce.product;

import com.viraj.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(@Valid ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);

        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponce> purchaseProduct(List<ProductPurchaseRequest> productPurchaseRequests) {
        var productIds = productPurchaseRequests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var products = productRepository.findAllByIdInOrderById(productIds);

        if(productIds.size() != products.size()){
            throw new ProductPurchaseException("Product(/s) not found");
        }

        var storedRequest = productPurchaseRequests.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponce>();

        for (int i = 0; i < products.size(); i++) {
            var product = products.get(i);

            var productRequest = storedRequest.get(i);

            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Product " + product.getName() + " is out of stock");
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);

            purchasedProducts.add(productMapper.toProductPurchaseResponce(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponce getProduct(Integer productId) {
        var product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return productMapper.toProductResponce(product);
    }

    public List<ProductResponce> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponce)
                .toList();
    }
}
