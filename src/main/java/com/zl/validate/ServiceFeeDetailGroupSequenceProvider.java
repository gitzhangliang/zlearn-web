package com.zl.validate;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2020/1/9.
 */
public class ServiceFeeDetailGroupSequenceProvider implements DefaultGroupSequenceProvider<ServiceFeeDetail> {
    @Override
    public List<Class<?>> getValidationGroups(ServiceFeeDetail bean) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(ServiceFeeDetail.class);

        if (bean != null) {
            String certificateName = bean.getCertificateName();
            if ("身份证".equals(certificateName)) {
                defaultGroupSequence.add(ServiceFeeDetail.IdCardGroup.class);
            }
        }
        return defaultGroupSequence;
    }
}
