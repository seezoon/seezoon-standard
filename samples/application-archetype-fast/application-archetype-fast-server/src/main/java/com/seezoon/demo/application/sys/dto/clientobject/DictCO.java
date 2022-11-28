package com.seezoon.demo.application.sys.dto.clientobject;

import lombok.Getter;
import lombok.Setter;

/**
 * 字典
 * 
 * @author dfenghuang
 * @date 2022/11/25 11:08
 */
@Getter
@Setter
public class DictCO {

    private String type;
    private Object value;
    private String label;
    private boolean disabled;

    public DictCO(String type, Object value, String label, boolean disabled) {
        this.type = type;
        this.value = value;
        this.label = label;
        this.disabled = disabled;
    }
}
