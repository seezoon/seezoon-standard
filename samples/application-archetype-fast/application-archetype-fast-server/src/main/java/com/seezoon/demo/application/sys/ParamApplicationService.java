package com.seezoon.demo.application.sys;

import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.dto.clientobject.SystemInfoCO;
import com.seezoon.demo.application.sys.executor.SystemParamQryExe;

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
