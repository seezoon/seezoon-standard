package com.seezoon.application.sys.executor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.BaseSpringApplicationTest;
import com.seezoon.application.sys.UserApplicationService;
import com.seezoon.application.sys.dto.UserByIdQry;

class UserByIdQryExeTest extends BaseSpringApplicationTest {

    @Autowired
    private UserByIdQryExe userByIdQryExe;

    @Autowired
    private UserApplicationService userApplicationService;

    @Test
    void execute() {
        UserByIdQry userByIdQry = new UserByIdQry(1);
        userByIdQryExe.execute(userByIdQry);
        userApplicationService.qryUserById(userByIdQry);
    }
}