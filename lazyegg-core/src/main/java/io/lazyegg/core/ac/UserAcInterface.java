package io.lazyegg.core.ac;

import io.lazyegg.core.UserInfo;

/**
 * UserAcInterface
 * 用户防腐层接口
 *
 * @author DifferentW  nsmeng3@163.com 2023/2/27 23:23
 */
public interface UserAcInterface {

    UserInfo getUserInfo(String username);
}
