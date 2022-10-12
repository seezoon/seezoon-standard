package com.seezoon.application.sys.executor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.BaseSpringApplicationTest;
import com.seezoon.application.sys.UserApplicationService;
import com.seezoon.application.sys.dto.QryUserById;

class QryUserByIdExeTest extends BaseSpringApplicationTest {

    @Autowired
    private QryUserByIdExe qryUserByIdExe;

    @Autowired
    private UserApplicationService userApplicationService;

    @Test
    void execute() {
        QryUserById qryUserById = new QryUserById(1);
        qryUserByIdExe.execute(qryUserById);
        userApplicationService.qryUserById(qryUserById);
    }
}