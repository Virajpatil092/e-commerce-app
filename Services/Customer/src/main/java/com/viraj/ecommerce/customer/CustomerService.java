package com.viraj.ecommerce.customer;

import com.viraj.ecommerce.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customerRequest) {
        var customer = customerRepo.save(customerMapper.toCustomer(customerRequest));

        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepo.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        customerMapper.updateCustomer(customer, customerRequest);

        customerRepo.save(customer);
    }

    public List<CustomerResponce> getAllCustomers() {
        return customerRepo.findAll().stream()
                .map(customerMapper::toCustomerResponce)
                .toList();
    }


    public Boolean existById(String customerId) {
        return customerRepo.existsById(customerId);
    }

    public CustomerResponce findById(String customerId) {
        var customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        return customerMapper.toCustomerResponce(customer);
    }

    public void deleteCustomer(String customerId) {
        customerRepo.deleteById(customerId);
    }
}
