#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.executor;

import ${package}.BaseSpringApplicationTest;
import ${package}.application.sys.dto.QryUserById;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class QryUserByIdExeTest extends BaseSpringApplicationTest {

    @MockBean
    private SysUserRepository sysUserRepository;
    @Autowired
    private QryUserByIdExe qryUserByIdExe;

    @BeforeEach
    void before() {
        SysUserPO sysUserPO = new SysUserPO();
        sysUserPO.setUserId(1);
        Mockito.when(sysUserRepository.find(Mockito.anyInt())).thenReturn(sysUserPO);
    }

    @Test
    void execute() {
        QryUserById qryUserById = new QryUserById(1);
        qryUserByIdExe.execute(qryUserById);
    }
}