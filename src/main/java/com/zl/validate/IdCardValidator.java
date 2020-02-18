package com.zl.validate;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhangliang
 * @date 2020/1/7.
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return new IdCardUtil(value).validate();
    }
}
