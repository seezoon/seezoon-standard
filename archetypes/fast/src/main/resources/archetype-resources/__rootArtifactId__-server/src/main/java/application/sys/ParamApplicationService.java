#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.sys;

import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Response;
import ${package}.application.sys.dto.clientobject.SystemInfoCO;
import ${package}.application.sys.executor.SystemParamQryExe;

import lombok.RequiredArgsConstructor;

/**
 * @author dfenghuang
 * @date 2022/11/25 12:11
 */
@ApplicationService
@RequiredArgsConstructor
public class ParamApplicationService {

    private final SystemParamQryExe systemParamQryExe;

    public Response<SystemInfoCO> qrySystemParam() {
        return systemParamQryExe.execute();
    }

}
