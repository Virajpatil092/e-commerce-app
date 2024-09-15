package com.viraj.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .id(orderRequest.id())
                .customerId(orderRequest.customerId())
                .reference(orderRequest.reference())
                .paymentMethod(orderRequest.paymentMethod())
                .totalAmount(orderRequest.amount())
                .build();
    }

    public OrderResponce toOrderResponce(Order order) {
        return new OrderResponce(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
