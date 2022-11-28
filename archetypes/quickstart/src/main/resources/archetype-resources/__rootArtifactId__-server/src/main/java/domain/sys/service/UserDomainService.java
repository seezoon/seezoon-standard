#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.service;

import com.seezoon.ddd.exception.Assertion;
import com.seezoon.ddd.exception.ExceptionFactory;
import ${package}.domain.sys.repository.SysUserRepository;
import ${package}.domain.sys.repository.po.SysUserPO;
import ${package}.infrastructure.error.ErrorCode;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 领域服务，应用层逻辑比较薄，通常是串流程，如果应用层较为复杂，比如出现如下逻辑则可以沉淀到领域服务：
 *
 * <p>
 * 1、较多或者较深的if
 * 2、应用可复用公共逻辑
 * 3、较多状态或者前置条件检查
 * 4、等等
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Validated
public class UserDomainService {

    private final SysUserRepository sysUserRepository;

    public void modifyUserMobile(@NotNull Integer userId, @NotEmpty String mobile, @NotEmpty String code) {
        SysUserPO sysUserPO = sysUserRepository.find(userId);
        if (null == sysUserPO) {
            throw ExceptionFactory.bizException(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }
        // 验证验证码
        this.checkMobileVerifyCode(mobile, code);

        // 更细手机号，按需更新，也可以修改userPO手机号全量更新，仓储服务会自动刷新更新时间
        SysUserPO toUpdate = new SysUserPO();
        toUpdate.setUserId(userId);
        toUpdate.setMobile(mobile);
        int affectedRows = this.sysUserRepository.updateSelective(toUpdate);
        // 针对这种极端的场景，可以不用单独定义错误码，或者统一定一个影响行数不符合预期的错误码
        Assertion.isTrue(affectedRows == 1, "user id [" + userId + "] change mobile affected Rows = " + affectedRows);
    }

    /**
     * 验证短信，如果返回的结果有多个值，可以建一个值对象，比如短信验证结果枚举或者常量
     *
     * @return
     */
    private void checkMobileVerifyCode(String mobile, String code) {
        log.debug("checkMobileVerifyCode mobile:{},code:{}", mobile, code);
        // 模拟验证短信
        // 也可以是rpc 远程调用接口，如果对方接口太差或者未来可能更换，可以包一层防腐层。
        // 有错误通过业务异常抛出
    }
}
