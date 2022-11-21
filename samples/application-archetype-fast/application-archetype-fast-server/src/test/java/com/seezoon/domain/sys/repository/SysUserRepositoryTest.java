package com.seezoon.domain.sys.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.BaseSpringApplicationTest;
import com.seezoon.domain.sys.repository.po.SysUserPO;

/**
 * @author dfenghuang
 * @date 2022/11/12 10:55
 */
class SysUserRepositoryTest extends BaseSpringApplicationTest {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Test
    public void mockData() {
        SysUserPO sysUserPO = new SysUserPO();
        for (int i = 0; i < 1000; i++) {
            sysUserPO.setUsername("testUserName" + i);
            sysUserPO.setName("testName" + i);
            sysUserRepository.save(sysUserPO);
        }
    }
}