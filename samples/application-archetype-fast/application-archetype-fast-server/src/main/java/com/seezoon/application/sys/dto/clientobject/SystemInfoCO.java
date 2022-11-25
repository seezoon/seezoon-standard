package com.seezoon.application.sys.dto.clientobject;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统参数
 * 
 * @author dfenghuang
 * @date 2022/11/25 11:10
 */
@Getter
@Setter
public class SystemInfoCO {

    private String fileUrlPrefix;

    private List<DictCO> dicts = Collections.EMPTY_LIST;
}
