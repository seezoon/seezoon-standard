#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys;

import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Page;
import com.seezoon.ddd.dto.Response;
import ${package}.application.sys.dto.AddUserCmd;
import ${package}.application.sys.dto.ModifyUserMobileCmd;
import ${package}.application.sys.dto.QryUserById;
import ${package}.application.sys.dto.QryUserPage;
import ${package}.application.sys.dto.clientobject.UserCO;
import ${package}.application.sys.executor.AddUserCmdExe;
import ${package}.application.sys.executor.ModifyUserMobileCmdExe;
import ${package}.application.sys.executor.QryUserByIdExe;
import ${package}.application.sys.executor.QryUserPageExe;

import lombok.RequiredArgsConstructor;

/**
 * 用户相关应用层服务 职责：应用层中的服务可以组合领域中的行为，也可以跨聚合调用(领域中的分包)
 */
@ApplicationService
@RequiredArgsConstructor
public class UserApplicationService {

    private final AddUserCmdExe addUserCmdExe;
    private final QryUserByIdExe qryUserByIdExe;
    private final ModifyUserMobileCmdExe modifyUserMobileCmdExe;
    private final QryUserPageExe qryUserPageExe;

    public Response addUser(AddUserCmd cmd) {
        return addUserCmdExe.execute(cmd);
    }

    public Response<UserCO> qryUserById(QryUserById qry) {
        return qryUserByIdExe.execute(qry);
    }

    public Response modifyUserMobile(ModifyUserMobileCmd cmd) {
        return modifyUserMobileCmdExe.execute(cmd);
    }

    public Response<Page<UserCO>> qryUserPage(QryUserPage qry) {
        return qryUserPageExe.execute(qry);
    }

}
