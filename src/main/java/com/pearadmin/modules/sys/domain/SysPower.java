package com.pearadmin.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pearadmin.common.web.base.BaseDomain;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * Describe: 权限领域模型
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Data
@Alias("SysPower")
public class SysPower extends BaseDomain {

    /**
     * 编号
     */
    @TableId
    private String powerId;

    /**
     * 权限名称
     */
    private String powerName;

    /**
     * 类型
     */
    private String powerType;

    /**
     * 标识
     */
    private String powerCode;

    /**
     * 路径
     */
    private String powerUrl;

    /**
     * 打开方式
     */
    private String openType;

    /**
     * 父级编号
     */
    private String parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 开启
     */
    private Boolean enable;

    /**
     * 计算列 提供给前端组件
     */
    @TableField(exist = false)
    private String checkArr = "0";
}
