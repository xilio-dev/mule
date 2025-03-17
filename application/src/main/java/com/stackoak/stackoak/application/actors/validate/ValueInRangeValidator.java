package com.stackoak.stackoak.application.actors.validate;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

/**
 * 输入的值必须在一个[]范围内
 */
public class ValueInRangeValidator implements ConstraintValidator<ValueInList, String> {
    private List<String> allowedValues;

    @Override
    public void initialize(ValueInList constraintAnnotation) {
        // 初始化时获取允许的值范围
        this.allowedValues = Arrays.asList(constraintAnnotation.allowedValues());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果值为空，默认通过验证（如果需要非空验证，可以结合 @NotNull）
        if (value == null) {
            return true;
        }
        // 检查值是否在允许的范围内
        return allowedValues.contains(value);
    }
}
