package com.zl.model.sso;

import lombok.Data;

/**
 * @author tzxx
 * @date 2019/3/6.
 */
@Data
public class SSOCallBack {
    private String content;
    private long time;
    private boolean isLogin;
    private String mobile;
    private String sign;
}
