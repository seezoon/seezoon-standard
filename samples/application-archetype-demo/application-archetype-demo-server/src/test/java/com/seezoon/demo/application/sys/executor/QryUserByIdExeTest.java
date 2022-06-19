package com.seezoon.demo.application.sys.executor;

import com.seezoon.ddd.context.SpringContextHolder;
import com.seezoon.demo.BaseSpringApplicationTest;
import com.seezoon.demo.application.sys.UserApplicationService;
import com.seezoon.demo.application.sys.dto.QryUserById;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QryUserByIdExeTest extends BaseSpringApplicationTest {

    @Autowired
    private QryUserByIdExe qryUserByIdExe;

    @Autowired
    private UserApplicationService userApplicationService;

    @Test
    void execute() {
        QryUserById qryUserById = new QryUserById(1);
        //qryUserByIdExe.execute(qryUserById);
        QryUserByIdExe bean = SpringContextHolder.getBean(QryUserByIdExe.class);
        userApplicationService.qryUserById(qryUserById);
    }
}