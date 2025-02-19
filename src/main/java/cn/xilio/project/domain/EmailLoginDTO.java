package cn.xilio.project.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailLoginDTO extends BaseDTO{
    @Email
    private String email;
    @NotEmpty
    private String password;
}
