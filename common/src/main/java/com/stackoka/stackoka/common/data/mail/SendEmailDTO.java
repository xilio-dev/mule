package com.stackoka.stackoka.common.data.mail;

import com.stackoka.stackoka.common.data.BaseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SendEmailDTO extends BaseDTO {
    @Email
    @NotEmpty
    private String email;
}
