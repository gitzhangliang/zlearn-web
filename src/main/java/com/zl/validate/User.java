package com.zl.validate;

import javax.validation.ConstraintViolation;
import javax.validation.GroupSequence;
import javax.validation.Validation;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import java.util.Set;

public class User {

    @NotBlank(message = "firstname may be empty")
    private String firstname;
    @NotBlank(message = "middlename may be empty")
    private String middlename;
    @NotBlank(message = "lastname may be empty", groups = GroupA.class)
    private String lastname;
    @NotBlank(message = "country may be empty", groups = GroupB.class)
    private String country;


    public interface GroupA {
	}
	public interface GroupB {
	}
	// 组序列
	@GroupSequence({Default.class, GroupA.class, GroupB.class})
	public interface Group {
	}
    public static void main(String[] args)  {
        User user = new User();
        // 此处指定了校验组是：User.Group.class
        Set<ConstraintViolation<User>> result = Validation.buildDefaultValidatorFactory().getValidator().validate(user,Default.class, GroupA.class, GroupB.class);

        // 对结果进行遍历输出
        result.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
    }


}
