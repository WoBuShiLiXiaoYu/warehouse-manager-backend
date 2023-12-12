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
 * 商品表
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer productId;

    /**
     * 
     */
    private Integer storeId;

    /**
     * 
     */
    private Integer brandId;

    /**
     * 
     */
    private String productName;

    /**
     * 
     */
    private String productNum;

    /**
     * 
     */
    private Integer productInvent;

    /**
     * 
     */
    private Integer typeId;

    /**
     * 
     */
    private Integer supplyId;

    /**
     * 
     */
    private Integer placeId;

    /**
     * 
     */
    private Integer unitId;

    /**
     * 
     */
    private String introduce;

    /**
     * 0 下架 1 上架
     */
    private String upDownState;

    /**
     * 
     */
    private BigDecimal inPrice;

    /**
     * 
     */
    private BigDecimal salePrice;

    /**
     * 
     */
    private BigDecimal memPrice;

    /**
     * 
     */

    private Date createTime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 
     */
    private Integer createBy;

    /**
     * 
     */
    private Integer updateBy;

    /**
     * 
     */
    private String imgs;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productDate;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date suppDate;

    /**
     * 追加字段
     */
    @TableField(exist = false)
    private String brandName;// 品牌名称

    @TableField(exist = false)
    private String supplyName;// 供应商命名成

    @TableField(exist = false)
    private String placeName;// 产地名

    @TableField(exist = false)
    private String typeName;// 分类名称

    @TableField(exist = false)
    private Integer isOverDate;// 是否过期

    @TableField(exist = false)
    private String storeName;// 仓库名称

    @TableField(exist = false)
    private String unitName;// 单位

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}