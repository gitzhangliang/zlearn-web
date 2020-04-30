package com.zl.validate;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author zl
 * @date 2019/12/17.
 */
@GroupSequenceProvider(ServiceFeeDetailGroupSequenceProvider.class)
@Data
public class ServiceFeeDetail {
    @NotNull
    private String name;

    @NotNull
    private String content;
    @ExcelProperty("证件名称")
    @NotBlank(message = "证件名称不能为空")
    private String certificateName;

    @ExcelProperty("证件号码")
    @NotBlank(message = "证件号码不能为空")
    @IdCard(message = "身份证号码格式错误",groups = IdCardGroup.class)
    private String certificateNumber;

    public interface IdCardGroup{}

    public static void main(String[] args)  {
        ServiceFeeDetail person = new ServiceFeeDetail();
        person.setCertificateName("身份证");
        person.setCertificateNumber("123");

        Set<ConstraintViolation<ServiceFeeDetail>> result = Validation.buildDefaultValidatorFactory().getValidator().validate(person);

        // 对结果进行遍历输出
        result.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
    }
}
