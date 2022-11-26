package com.seezoon.application.sys;

import com.seezoon.application.sys.dto.clientobject.SystemInfoCO;
import com.seezoon.application.sys.executor.SystemParamQryExe;
import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Response;

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
