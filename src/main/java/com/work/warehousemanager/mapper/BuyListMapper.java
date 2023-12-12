package com.work.warehousemanager.mapper;

import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.BuyList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【buy_list(采购单)】的数据库操作Mapper
* @createDate 2023-12-06 19:59:36
* @Entity com.work.warehousemanager.entity.BuyList
*/
public interface BuyListMapper extends BaseMapper<BuyList> {

    /**
     * 按条件查询总行数
     * @param buyList
     * @return
     */
    Integer selectCountByCriteria(@Param("buyList") BuyList buyList);

    /**
     * 按条件分页查询
     * @param page
     * @param buyList
     * @return
     */
    List<BuyList> selectListByCriteria(@Param("page") Page page, @Param("buyList") BuyList buyList);

}




