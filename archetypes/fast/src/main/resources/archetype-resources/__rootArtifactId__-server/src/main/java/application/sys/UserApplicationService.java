#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys;

import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Page;
import com.seezoon.ddd.dto.Response;
import ${package}.application.sys.dto.AddUserCmd;
import ${package}.application.sys.dto.ChangeMyPwdCmd;
import ${package}.application.sys.dto.ChangeUserPwdCmd;
import ${package}.application.sys.dto.DeleteUserByIdCmd;
import ${package}.application.sys.dto.ModifyUserCmd;
import ${package}.application.sys.dto.UserByIdQry;
import ${package}.application.sys.dto.UserPageQry;
import ${package}.application.sys.dto.clientobject.PersonalInfoCO;
import ${package}.application.sys.dto.clientobject.UserCO;
import ${package}.application.sys.executor.AddUserCmdExe;
import ${package}.application.sys.executor.ChangeMyPwdCmdExe;
import ${package}.application.sys.executor.ChangeUserPwdCmdExe;
import ${package}.application.sys.executor.DeleteUserByIdCmdExe;
import ${package}.application.sys.executor.ModifyUserCmdExe;
import ${package}.application.sys.executor.PersonalInfoQryExe;
import ${package}.application.sys.executor.UserByIdQryExe;
import ${package}.application.sys.executor.UserPageQryExe;

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
