package com.work.warehousemanager.mapper;

import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.OutStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【out_store(出库单)】的数据库操作Mapper
* @createDate 2023-12-06 20:38:44
* @Entity com.work.warehousemanager.entity.OutStore
*/
public interface OutStoreMapper extends BaseMapper<OutStore> {

    /**
     * 按条件查询总行数
     * @param outStore
     * @return
     */
    Integer selectCountByCriteria(@Param("outStore") OutStore outStore);

    /**
     * 按条件分页查询
     * @param page
     * @param outStore
     * @return
     */
    List<OutStore> selectListByCriteria(@Param("page") Page page, @Param("outStore") OutStore outStore);
}




