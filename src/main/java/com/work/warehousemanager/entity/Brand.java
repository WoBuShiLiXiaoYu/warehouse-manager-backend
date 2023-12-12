package com.work.warehousemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 品牌
 * @TableName brand
 */
@TableName(value ="brand")
@Data
public class Brand implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer brandId;

    /**
     * 
     */
    private String brandName;

    /**
     * 
     */
    private String brandLeter;

    /**
     * 
     */
    private String brandDesc;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}