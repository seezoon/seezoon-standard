package com.seezoon.mybatis.repository.spi;

/**
 * 用户上下文 如有需要SPI注入
 * 在resources目录下新建META-INF/services/com.seezoon.mybatis.repository.spi.UserContext
 * 内容填实现的类的全称
 */
public interface UserContext {

    default Object getId() {
        return null;
    }
}

class DefaultUserContext implements UserContext {

}