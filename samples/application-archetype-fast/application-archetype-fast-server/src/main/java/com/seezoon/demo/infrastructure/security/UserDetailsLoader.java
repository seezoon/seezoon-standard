package com.seezoon.demo.infrastructure.security;

/**
 * @author dfenghuang
 * @date 2022/10/12 22:25
 */
public interface UserDetailsLoader {
    UserInfoDetails loadByUserId(Integer userId);
}
