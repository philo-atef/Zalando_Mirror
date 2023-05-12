package com.example.demo.customer;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @PutMapping("/editProfile")
    public void editCustomerProfile(
            @RequestBody CustomerEditRequest customerEditRequest,
            HttpServletRequest request
    ) {
        customerService.editCustomerProfile(customerEditRequest, request);
    }

    @PostMapping("/deleteAccount")
    public void deleteAccount(
            HttpServletRequest request
    ) {
        customerService.deleteAccount(request);
    }
}
