package com.seezoon.interfaces.sys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.application.sys.SysParamApplicationService;
import com.seezoon.application.sys.dto.clientobject.SystemInfoCO;
import com.seezoon.ddd.dto.Response;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * 系统参数
 * 
 * @author dfenghuang
 * @date 2022/11/25 12:08
 */
@RestController
@RequestMapping("/sys/param")
@RequiredArgsConstructor
@Tag(name = "系统参数", description = "管理系统参数，字典等")
public class SysParamController {

    private final SysParamApplicationService sysParamApplicationService;

    @GetMapping("/qry/all")
    public Response<SystemInfoCO> qrySystemParam() {
        return sysParamApplicationService.qrySystemParam();
    }
}
