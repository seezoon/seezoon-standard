#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.interfaces.sys.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.ddd.dto.Page;
import com.seezoon.ddd.dto.Response;
import ${package}.application.sys.UserApplicationService;
import ${package}.application.sys.dto.AddUserCmd;
import ${package}.application.sys.dto.ModifyUserMobileCmd;
import ${package}.application.sys.dto.QryUserById;
import ${package}.application.sys.dto.QryUserPage;
import ${package}.application.sys.dto.clientobject.UserCO;

import lombok.RequiredArgsConstructor;

/**
 * 用户信息
 */
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
@Validated
public class SysUserController {

    private final UserApplicationService userApplicationService;

    @PostMapping("/add_user")
    public Response addUser(@RequestBody AddUserCmd cmd) {
        return userApplicationService.addUser(cmd);
    }

    /**
     * 通过id查询用户
     *
     * @param id 用户id 可以遵循restful 风格，也可以统一使用http+json 方式接收参数
     * @return
     */
    @GetMapping("/qry/{id}")
    public Response<UserCO> qryUserById(@PathVariable Integer id) {
        QryUserById qry = new QryUserById(id);
        return userApplicationService.qryUserById(qry);
    }

    /**
     * 修改用户手机号
     *
     * @param cmd
     * @return
     */
    @PostMapping("/modify_user_mobile")
    public Response modifyUserMobile(@RequestBody ModifyUserMobileCmd cmd) {
        return this.userApplicationService.modifyUserMobile(cmd);
    }

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/qry_user_page")
    public Response<Page<UserCO>> qryUserPage(@RequestBody QryUserPage qry) {
        return this.userApplicationService.qryUserPage(qry);
    }

}
