package com.seezoon.demo.application.sys.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 删除用户
 */
@Getter
@Setter
public class DeleteUserByIdCmd {

    @NotNull
    private Integer userId;

    public DeleteUserByIdCmd(Integer userId) {
        this.userId = userId;
    }
}
