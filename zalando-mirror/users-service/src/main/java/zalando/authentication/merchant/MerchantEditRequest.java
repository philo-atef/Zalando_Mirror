package zalando.authentication.merchant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantEditRequest {
    private String email;
    private String password;
    private String brandName;
    private String hotline;
}
