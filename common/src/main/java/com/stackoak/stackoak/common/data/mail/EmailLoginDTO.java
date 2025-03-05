package com.stackoak.stackoak.common.data.mail;

import com.stackoak.stackoak.common.data.BaseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailLoginDTO extends BaseDTO {
    @Email(message = "邮箱格式不正确")
    @NotEmpty(message = "邮箱为空")
    private String email;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
