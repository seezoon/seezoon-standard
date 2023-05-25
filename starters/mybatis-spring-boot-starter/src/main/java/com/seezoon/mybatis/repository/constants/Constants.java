package com.seezoon.mybatis.repository.constants;

import java.util.Objects;

/**
 * @author hdf
 */
public class Constants {

    public static final int NORMAL = 1;
    public static final int INVALID = 0;

    public static boolean isValid(Integer status) {
        return Objects.equals(status, NORMAL);
    }
}
