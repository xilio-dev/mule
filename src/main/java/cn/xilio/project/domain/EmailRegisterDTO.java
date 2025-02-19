package cn.xilio.project.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EmailRegisterDTO extends BaseDTO {
    @Email
    @NotEmpty
    private String email;

    private Integer code;
}
