package com.work.warehousemanager.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 用户表
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 
     */
    private String userCode;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String userPwd;

    /**
     * 1 超级管理员 、 2  管理员 、 3 普通用户
     */
    private String userType;

    /**
     * 0 未审核 、1 已审核
     */
    private String userState;

    /**
     * 0 正常、 1 已删除
     */
    @TableLogic
    private String isDelete;

    /**
     * 
     */
    private Integer createBy;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 
     */
    private Integer updateBy;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     *  追加字段    创建人昵称
     */
    @TableField(exist = false)
    private String getCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}