package com.seezoon.demo.application.sys;

import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Page;
import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.dto.AddUserCmd;
import com.seezoon.demo.application.sys.dto.ChangeMyPwdCmd;
import com.seezoon.demo.application.sys.dto.ChangeUserPwdCmd;
import com.seezoon.demo.application.sys.dto.DeleteUserByIdCmd;
import com.seezoon.demo.application.sys.dto.ModifyUserCmd;
import com.seezoon.demo.application.sys.dto.UserByIdQry;
import com.seezoon.demo.application.sys.dto.UserPageQry;
import com.seezoon.demo.application.sys.dto.clientobject.PersonalInfoCO;
import com.seezoon.demo.application.sys.dto.clientobject.UserCO;
import com.seezoon.demo.application.sys.executor.AddUserCmdExe;
import com.seezoon.demo.application.sys.executor.ChangeMyPwdCmdExe;
import com.seezoon.demo.application.sys.executor.ChangeUserPwdCmdExe;
import com.seezoon.demo.application.sys.executor.DeleteUserByIdCmdExe;
import com.seezoon.demo.application.sys.executor.ModifyUserCmdExe;
import com.seezoon.demo.application.sys.executor.PersonalInfoQryExe;
import com.seezoon.demo.application.sys.executor.UserByIdQryExe;
import com.seezoon.demo.application.sys.executor.UserPageQryExe;

import lombok.RequiredArgsConstructor;

/**
 * 用户相关应用层服务 职责：应用层中的服务可以组合领域中的行为，也可以跨聚合调用(领域中的分包)
 */
@ApplicationService
@RequiredArgsConstructor
public class UserApplicationService {

    private final AddUserCmdExe addUserCmdExe;
    private final UserByIdQryExe userByIdQryExe;
    private final ModifyUserCmdExe modifyUserCmdExe;
    private final UserPageQryExe userPageQryExe;
    private final DeleteUserByIdCmdExe deleteUserByIdCmdExe;
    private final ChangeUserPwdCmdExe changeUserPwdCmdExe;

    private final PersonalInfoQryExe personalInfoQryExe;

    private final ChangeMyPwdCmdExe changeMyPwdCmdExe;

    public Response deleteUserById(DeleteUserByIdCmd cmd) {
        return deleteUserByIdCmdExe.execute(cmd);
    }

    public Response changeUserPwd(ChangeUserPwdCmd cmd) {
        return changeUserPwdCmdExe.execute(cmd);
    }

    public Response changeMyPwd(ChangeMyPwdCmd cmd) {
        return changeMyPwdCmdExe.execute(cmd);
    }

    public Response addUser(AddUserCmd cmd) {
        return addUserCmdExe.execute(cmd);
    }

    public Response<UserCO> qryUserById(UserByIdQry qry) {
        return userByIdQryExe.execute(qry);
    }

    public Response<PersonalInfoCO> qryPersonalInfo() {
        return personalInfoQryExe.execute();
    }

    public Response modifyUser(ModifyUserCmd cmd) {
        return modifyUserCmdExe.execute(cmd);
    }

    public Response<Page<UserCO>> qryUserPage(UserPageQry qry) {
        return userPageQryExe.execute(qry);
    }

}
