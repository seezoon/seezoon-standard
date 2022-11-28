#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import ${package}.BaseSpringApplicationTest;
import ${package}.domain.sys.repository.po.SysUserPO;

/**
 * @author dfenghuang
 * @date 2022/11/12 10:55
 */
class SysUserRepositoryTest extends BaseSpringApplicationTest {

    /**
     * 全mock
     */
    @MockBean
    private SysUserRepository sysUserRepository;

    @BeforeEach
    void before() {
        when(sysUserRepository.save(any(SysUserPO.class))).thenReturn(1);
    }

    @Test
    public void save() {
        SysUserPO sysUserPO = new SysUserPO();
        sysUserPO.setUserId(1);
        sysUserPO.setUsername("testUserName");
        int affectedRows = sysUserRepository.save(sysUserPO);
        Assertions.assertEquals(affectedRows, 1);

    }
}