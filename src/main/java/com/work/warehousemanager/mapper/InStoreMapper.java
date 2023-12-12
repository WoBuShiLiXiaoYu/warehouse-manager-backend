package com.work.warehousemanager.mapper;

import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.InStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【in_store(入库单)】的数据库操作Mapper
* @createDate 2023-12-07 11:13:08
* @Entity com.work.warehousemanager.entity.InStore
*/
public interface InStoreMapper extends BaseMapper<InStore> {

    /**
     * 按条件统计总行数
     * @param inStore
     * @return
     */
    Integer selectCountByCriteria(@Param("inStore") InStore inStore);

    /**
     * 按条件分页查询
     * @param page
     * @param inStore
     * @return
     */
    List<InStore> selectListByCriteria(@Param("page") Page page, @Param("inStore") InStore inStore);

}




