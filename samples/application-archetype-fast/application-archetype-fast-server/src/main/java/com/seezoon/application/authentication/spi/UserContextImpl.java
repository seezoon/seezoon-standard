package com.seezoon.application.authentication.spi;

import com.seezoon.application.authentication.SecurityUtils;
import com.seezoon.mybatis.repository.spi.UserContext;

/**
 * 方便DB自动记录用户
 * 
 * @author dfenghuang
 * @date 2022/10/12 01:46
 */
public class UserContextImpl implements UserContext {
    @Override
    public Object getId() {
        Integer userId = SecurityUtils.getUserId();
        return null != userId ? userId : SecurityUtils.SUPER_ADMIN_USER_ID;
    }
}
