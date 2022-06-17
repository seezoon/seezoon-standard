package com.seezoon.mybatis.repository.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class UserContextLoader {

    private static final UserContext instance = load();

    private UserContextLoader() {
    }

    private static UserContext load() {
        ServiceLoader<UserContext> serviceLoader = ServiceLoader.load(UserContext.class);
        Iterator<UserContext> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            return iterator.next();
        }
        return new DefaultUserContext();
    }

    public static UserContext getInstance() {
        return instance;
    }
}
