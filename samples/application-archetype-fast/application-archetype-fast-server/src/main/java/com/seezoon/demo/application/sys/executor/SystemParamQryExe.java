package com.seezoon.demo.application.sys.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.dto.clientobject.DictCO;
import com.seezoon.demo.application.sys.dto.clientobject.SystemInfoCO;
import com.seezoon.demo.infrastructure.properties.SeezoonProperties;
import com.seezoon.demo.infrastructure.properties.SeezoonProperties.Dict;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统参数
 * 
 * @author dfenghuang
 * @date 2022/11/25 11:17
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class SystemParamQryExe {

    private final SeezoonProperties seezoonProperties;

    public Response<SystemInfoCO> execute() {
        SystemInfoCO systemInfoCO = new SystemInfoCO();
        systemInfoCO.setUploadUrl(seezoonProperties.getUpload().getUploadUrl());
        systemInfoCO.setFileUrlPrefix(seezoonProperties.getUpload().getUrlPrefix());
        Map<String, List<Dict>> allDicts = seezoonProperties.getDicts();
        List<DictCO> dicts = new ArrayList<>();
        for (Entry<String, List<Dict>> entry : allDicts.entrySet()) {
            entry.getValue().forEach(
                item -> dicts.add(new DictCO(entry.getKey(), item.getValue(), item.getLabel(), item.isDisabled())));
        }
        systemInfoCO.setDicts(dicts);
        return Response.success(systemInfoCO);
    }
}
