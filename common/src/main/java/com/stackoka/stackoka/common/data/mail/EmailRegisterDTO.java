package com.stackoka.stackoka.common.data.mail;

import com.stackoka.stackoka.common.data.BaseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailRegisterDTO extends BaseDTO {
    @Email
    @NotEmpty
    private String email;

    private Integer code;
}
