package com.viraj.ecommerce.order;

import com.viraj.ecommerce.customer.CustomerClient;
import com.viraj.ecommerce.exception.BussinessException;
import com.viraj.ecommerce.kafka.OrderConformation;
import com.viraj.ecommerce.kafka.OrderProducer;
import com.viraj.ecommerce.orderline.OrderLineRequest;
import com.viraj.ecommerce.orderline.OrderLineService;
import com.viraj.ecommerce.payment.PaymentClient;
import com.viraj.ecommerce.payment.PaymentRequest;
import com.viraj.ecommerce.product.ProductClient;
import com.viraj.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest orderRequest) {
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BussinessException("Customer not found"));


        var purchasedProducts = this.productClient.purchaseProducts(orderRequest.purchaseRequests());

        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

        for(PurchaseRequest purchaseRequest: orderRequest.purchaseRequests()) {
            orderLineService.saveOrder(
              new OrderLineRequest(
                      null,
                      order.getId(),
                      purchaseRequest.productId(),
                      purchaseRequest.quantity()
              )
            );
        }

        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConformation(
                new OrderConformation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponce> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponce)
                .toList();
    }

    public OrderResponce findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderResponce)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}
