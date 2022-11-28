#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys.executor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.seezoon.ddd.dto.Response;
import ${package}.BaseSpringApplicationTest;
import ${package}.application.sys.dto.UserByIdQry;
import ${package}.application.sys.dto.clientobject.UserCO;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;

class UserByIdQryExeTest extends BaseSpringApplicationTest {

    /**
     * 部分mock
     */
    @SpyBean
    public SysUserRepository sysUserRepository;

    @Autowired
    private UserByIdQryExe userByIdQryExe;

    @BeforeEach
    void before() {
        SysUserPO po = new SysUserPO();
        po.setUserId(1);
        po.setName("testname");
        when(sysUserRepository.find(any(Integer.class))).thenReturn(po);
    }

    @Test
    void execute() {
        UserByIdQry userByIdQry = new UserByIdQry(1);
        Response<UserCO> response = userByIdQryExe.execute(userByIdQry);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(1, response.getData().getUserId());
    }
}