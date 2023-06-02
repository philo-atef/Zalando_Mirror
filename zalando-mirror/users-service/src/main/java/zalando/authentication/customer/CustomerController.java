package zalando.authentication.customer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

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
