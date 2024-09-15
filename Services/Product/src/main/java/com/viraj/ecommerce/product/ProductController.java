package com.viraj.ecommerce.product;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest productRequest
    ) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponce>> purchaseProduct(
            @RequestBody List<ProductPurchaseRequest> productPurchaseRequests
    ) {
        return ResponseEntity.ok(productService.purchaseProduct(productPurchaseRequests));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponce> getProduct(
            @PathVariable("productId") Integer productId
    ) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponce>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
}
