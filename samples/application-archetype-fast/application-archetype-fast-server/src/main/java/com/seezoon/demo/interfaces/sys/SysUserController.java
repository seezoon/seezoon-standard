package com.seezoon.demo.interfaces.sys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.ddd.dto.Page;
import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.UserApplicationService;
import com.seezoon.demo.application.sys.dto.AddUserCmd;
import com.seezoon.demo.application.sys.dto.ChangeMyPwdCmd;
import com.seezoon.demo.application.sys.dto.ChangeUserPwdCmd;
import com.seezoon.demo.application.sys.dto.DeleteUserByIdCmd;
import com.seezoon.demo.application.sys.dto.ModifyUserCmd;
import com.seezoon.demo.application.sys.dto.UserByIdQry;
import com.seezoon.demo.application.sys.dto.UserPageQry;
import com.seezoon.demo.application.sys.dto.clientobject.PersonalInfoCO;
import com.seezoon.demo.application.sys.dto.clientobject.UserCO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * 用户信息
 */
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "提供用户信息管理接口")
public class SysUserController {

    private final UserApplicationService userApplicationService;

    @PostMapping("/add")
    public Response add(@RequestBody AddUserCmd cmd) {
        return userApplicationService.addUser(cmd);
    }

    @PostMapping("/delete/{userId}")
    public Response deleteUserById(@PathVariable Integer userId) {
        return userApplicationService.deleteUserById(new DeleteUserByIdCmd(userId));
    }

    @PostMapping("/change/pwd")
    public Response changeUserPwd(@RequestBody ChangeUserPwdCmd cmd) {
        return userApplicationService.changeUserPwd(cmd);
    }

    @PostMapping("/change/my_pwd")
    public Response changeMyPwd(@RequestBody ChangeMyPwdCmd cmd) {
        return userApplicationService.changeMyPwd(cmd);
    }

    /**
     * 通过id查询用户
     *
     * @param userId 用户id 可以遵循restful 风格，也可以统一使用http+json 方式接收参数
     * @return
     */
    @GetMapping("/qry/{userId}")
    public Response<UserCO> qryUserById(@PathVariable Integer userId) {
        UserByIdQry qry = new UserByIdQry(userId);
        return userApplicationService.qryUserById(qry);
    }

    /**
     * 查询个人信息
     * 
     * @return
     */
    @GetMapping("/personal")
    public Response<PersonalInfoCO> qryPersonalInfo() {
        return userApplicationService.qryPersonalInfo();
    }

    /**
     * 修改用户信息
     *
     * @param cmd
     * @return
     */
    @PostMapping("/modify")
    public Response modifyUserMobile(@RequestBody ModifyUserCmd cmd) {
        return this.userApplicationService.modifyUser(cmd);
    }

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/qry_user_page")
    public Response<Page<UserCO>> qryUserPage(@RequestBody UserPageQry qry) {
        return this.userApplicationService.qryUserPage(qry);
    }

}
