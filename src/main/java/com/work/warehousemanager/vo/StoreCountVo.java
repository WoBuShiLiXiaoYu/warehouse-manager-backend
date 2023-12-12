package com.work.warehousemanager.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCountVo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = -4589721226525784702L;

    private Integer storeId;

    private String storeName;

    private Integer totalInvent;// 仓库商品数量
}
