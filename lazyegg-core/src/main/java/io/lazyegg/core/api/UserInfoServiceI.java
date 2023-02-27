package io.lazyegg.core.api;

import io.lazyegg.core.UserInfo;

/**
 * UserInfoServiceI
 * 用户信息接口
 *
 * @author DifferentW  nsmeng3@163.com 2023/2/27 23:23
 */
public interface UserInfoServiceI {

    UserInfo getUserInfo(String username);
}
