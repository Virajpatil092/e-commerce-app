package com.viraj.ecommerce.customer;

public record CustomerResponce(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
