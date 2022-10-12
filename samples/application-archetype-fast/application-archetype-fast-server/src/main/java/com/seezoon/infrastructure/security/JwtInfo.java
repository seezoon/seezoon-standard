package com.seezoon.infrastructure.security;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.seezoon.ddd.exception.Assertion;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义信息
 * 
 * @author dfenghuang
 * @date 2022/10/12 11:05
 */
@Getter
@Setter
public class JwtInfo {

    private String userName;
    private Integer userId;

    /**
     * 关键字段sha1 比对，userId + password
     */
    private String checkSum;

    public JwtInfo() {}

    public JwtInfo(String userName, Integer userId, String checkSum) {
        Assertion.isTrue(StringUtils.isNotEmpty(userName));
        Assertion.notNull(userId);
        this.userName = userName;
        this.userId = userId;
        this.checkSum = checkSum;
    }

    public boolean check(String checkSum) {
        return Objects.equals(checkSum, checkSum);
    }

}
