package com.work.warehousemanager.mapper;

import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Store;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.warehousemanager.vo.StoreCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【store(仓库表)】的数据库操作Mapper
* @createDate 2023-12-05 18:19:36
* @Entity com.work.warehousemanager.entity.Store
*/
public interface StoreMapper extends BaseMapper<Store> {

    // 查询仓库下商品数量
    List<StoreCountVo> selectCountVo();

    /**
     * 按条件查询总行数
     * @param store
     * @return
     */
    Integer selectCountByCriteria(@Param("store") Store store);

    /**
     * 按条件分页查询
     * @param page
     * @param store
     * @return
     */
    List<Store> selectListByCriteria(@Param("page") Page page, @Param("store") Store store);
}




