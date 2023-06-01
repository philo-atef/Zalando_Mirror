package zalando.authentication.merchant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRegisterRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String brandName;
    @NotNull
    private String hotline;
    @NotNull
    private LocalDate dateJoined;
}
