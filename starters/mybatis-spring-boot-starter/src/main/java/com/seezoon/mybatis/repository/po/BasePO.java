package com.seezoon.mybatis.repository.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.mybatis.repository.constants.Constants;
import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 标识是持久化对象与DB一一对应
 *
 * @author hdf
 */
@Getter
@Setter
public class BasePO<PK> {

    /**
     * 主键只支持单一主键，数值或者字符串,名字可以用其他的,其他的代码生成器会覆写getId方法，方便使用。
     */
    private PK id;
    /**
     * 数据状态 一般表建议不要这个字段，设计上没有软删除场景，如果有就用
     *
     * {@link com.seezoon.mybatis.repository.constants.Constants#NORMAL }
     * {@link com.seezoon.mybatis.repository.constants.Constants#INVALID }
     *
     * 使用Integer 写代码更方便
     *
     * 保存方法自动处理
     * </pre>
     */
    @Max(127)
    @Min(-128)
    private Integer status = Constants.NORMAL;
    /**
     * 保存方法自动处理
     * 创建人
     */
    private Object createBy;
    /**
     * 保存方法自动处理
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新方法自动处理
     * 更新人
     */
    private Object updateBy;
    /**
     * 更新方法自动处理
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Size(max = 255)
    private String remarks;

}
