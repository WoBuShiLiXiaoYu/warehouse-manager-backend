package com.work.warehousemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 入库单
 * @TableName in_store
 */
@TableName(value ="in_store")
@Data
public class InStore implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer insId;

    /**
     * 
     */
    private Integer storeId;

    /**
     * 
     */
    private Integer productId;

    /**
     * 
     */
    private Integer inNum;

    /**
     * 
     */
    private Integer createBy;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 0 否 1 是
     */
    private String isIn;

    /**
     * 追加字段
     */
    @TableField(exist = false)
    private String productName;// 商品名

    @TableField(exist = false)
    private String startTime;// 开始时间

    @TableField(exist = false)
    private String endTime;// 结束时间

    @TableField(exist = false)
    private String storeName;// 仓库名

    @TableField(exist = false)
    private BigDecimal inPrice;// 入库价格

    @TableField(exist = false)
    private String userCode;// 创建人

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}