package study.LoginWithJWT.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    private String email;
    
    @NotNull
    private String password;
    
    @NotNull
    private String name;
    
    @NotNull
    private String loginId;
}
