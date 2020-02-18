package com.zl.validate;

import com.zl.utils.CalculateUtil;
import lombok.Data;
import lombok.ToString;
import org.apache.poi.poifs.property.Child;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@GroupSequenceProvider(PersonGroupSequenceProvider.class)
@Data
public class Person {

    @NotNull
    private String name;
    @NotNull
    @Range(min = 10, max = 40)
    private Integer age;

    @NotNull(groups = {WhenAge20And30Group.class, WhenAge30And40Group.class})
    @Size(min = 1, max = 2, groups = WhenAge20And30Group.class)
    @Size(min = 3, max = 5, groups = WhenAge30And40Group.class)
    private List<String> hobbies;


    /**
     * 定义专属的业务逻辑分组
     */
    public interface WhenAge20And30Group {
    }
    public interface WhenAge30And40Group {
    }
    public static void main(String[] args)  {
//        Person person = new Person();
//        person.setAge(20);
//        person.setName("fsx");
//
//        person.setHobbies(Arrays.asList());
//
//        Set<ConstraintViolation<Person>> result = Validation.buildDefaultValidatorFactory().getValidator().validate(person);
//
//        // 对结果进行遍历输出
//        result.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
        a();
    }

    public static void a(){
        BigDecimal b = new BigDecimal("3.0000");
        BigDecimal b1 = new BigDecimal("1.0000");
        BigDecimal subtract = CalculateUtil.subtract(b, b1, 2, RoundingMode.HALF_DOWN);
        System.out.println(subtract.toString());
    }
}
