package com.viraj.ecommerce.product;

import com.viraj.ecommerce.exception.BussinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public List<PurchaseResponce> purchaseProducts(List<PurchaseRequest> purchaseRequests){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> request = new HttpEntity<>(purchaseRequests, headers);
        ParameterizedTypeReference<List<PurchaseResponce>> responseType = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<PurchaseResponce>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                request,
                responseType
        );

        if(responseEntity.getStatusCode().isError()){
            throw new BussinessException("Error while purchasing products");
        }
        return responseEntity.getBody();
    }
}
