package com.seezoon.application.sys.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 通过用户id查询用户信息入参
 */
@Getter
@Setter
@AllArgsConstructor
public class QryUserById {

    /**
     * 用户id
     */
    @NotNull
    private Integer userId;

}
