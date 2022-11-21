package com.seezoon.application.sys;

import com.seezoon.application.sys.dto.AddUserCmd;
import com.seezoon.application.sys.dto.ChangeUserPwdCmd;
import com.seezoon.application.sys.dto.DeleteUserByIdCmd;
import com.seezoon.application.sys.dto.ModifyUserCmd;
import com.seezoon.application.sys.dto.UserByIdQry;
import com.seezoon.application.sys.dto.UserPageQry;
import com.seezoon.application.sys.dto.clientobject.UserCO;
import com.seezoon.application.sys.executor.AddUserCmdExe;
import com.seezoon.application.sys.executor.ChangeUserPwdCmdExe;
import com.seezoon.application.sys.executor.DeleteUserByIdCmdExe;
import com.seezoon.application.sys.executor.ModifyUserCmdExe;
import com.seezoon.application.sys.executor.UserByIdQryExe;
import com.seezoon.application.sys.executor.UserPageQryExe;
import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Page;
import com.seezoon.ddd.dto.Response;

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

    public Response deleteUserById(DeleteUserByIdCmd cmd) {
        return deleteUserByIdCmdExe.execute(cmd);
    }

    public Response changeUserPwd(ChangeUserPwdCmd cmd) {
        return changeUserPwdCmdExe.execute(cmd);
    }

    public Response addUser(AddUserCmd cmd) {
        return addUserCmdExe.execute(cmd);
    }

    public Response<UserCO> qryUserById(UserByIdQry qry) {
        return userByIdQryExe.execute(qry);
    }

    public Response modifyUser(ModifyUserCmd cmd) {
        return modifyUserCmdExe.execute(cmd);
    }

    public Response<Page<UserCO>> qryUserPage(UserPageQry qry) {
        return userPageQryExe.execute(qry);
    }

}
