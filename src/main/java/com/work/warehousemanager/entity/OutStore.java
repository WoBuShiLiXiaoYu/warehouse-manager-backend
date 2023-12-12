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
 * 出库单
 * @TableName out_store
 */
@TableName(value ="out_store")
@Data
public class OutStore implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer outsId;

    /**
     * 
     */
    private Integer productId;

    /**
     * 
     */
    private Integer storeId;

    /**
     * 
     */
    private Integer tallyId;

    /**
     * 
     */
    private BigDecimal outPrice;

    /**
     * 
     */
    private Integer outNum;

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
    private String isOut;

    /**
     * 追加字段
     */
    @TableField(exist = false)
    private BigDecimal salePrice;// 出库价格

    @TableField(exist = false)
    private String productName;// 商品名称

    @TableField(exist = false)
    private String startTime;// 起始时间

    @TableField(exist = false)
    private String endTime;// 结束时间

    @TableField(exist = false)
    private String storeName;// 仓库名称

    @TableField(exist = false)
    private String userCode;// 创建人

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}