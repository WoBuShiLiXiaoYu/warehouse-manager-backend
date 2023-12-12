package com.work.warehousemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * 权限表
 * @TableName auth_info
 */
@TableName(value ="auth_info")
@Data
@ToString
public class AuthInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer authId;//权限(菜单)id

    /**
     * 父id为空或为0，表示一级权限
     */
    private Integer parentId;//父权限(菜单)id

    /**
     * 
     */
    private String authName;//权限(菜单)名称

    /**
     * 
     */
    private String authDesc;//权限(菜单)描述


    /**
     * 
     */
    private Integer authGrade;//权限(菜单)层级


    /**
     * 1 模块 、2  列表、 3  按钮
     */
    private String authType;//权限(菜单)类型

    /**
     * 
     */
    private String authUrl;//权限(菜单)访问的url接口

    /**
     * 
     */
    private String authCode;//权限(菜单)标识

    /**
     * 
     */
    private Integer authOrder;//权限(菜单)的优先级

    /**
     * 1 启用 、0 禁用
     */
    private String authState;//权限(菜单)状态(1.启用,0.禁用)

    /**
     * 
     */
    private Integer createBy;//创建权限(菜单)的用户id

    /**
     * 
     */
    private Date createTime;//权限(菜单)的创建时间

    /**
     * 
     */
    private Integer updateBy;//修改权限(菜单)的用户id

    /**
     * 
     */
    private Date updateTime;//权限(菜单)的修改时间

    /**
     * 追加字段 用于存储当前权限(菜单)的子级权限(菜单)
     */
    @TableField(exist = false)
    private List<AuthInfo> childAuth;
    // 映射
    @TableField(exist = false)
    private List<AuthInfo> authIdList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}