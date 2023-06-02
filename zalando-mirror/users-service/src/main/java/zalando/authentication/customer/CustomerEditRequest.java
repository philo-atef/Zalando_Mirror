package zalando.authentication.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEditRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String telephoneNumber;
    private LocalDate dateOfBirth;
    private String creditCardNumber;

}