package com.work.warehousemanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.assignrole.AssignRoleDto;
import com.work.warehousemanager.entity.Role;
import com.work.warehousemanager.entity.UserRole;
import com.work.warehousemanager.mapper.RoleMapper;
import com.work.warehousemanager.service.UserRoleService;
import com.work.warehousemanager.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 胡国海
* @description 针对表【user_role(用户角色表)】的数据库操作Service实现
* @createDate 2023-12-04 17:40:32
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Result assignRole(AssignRoleDto assignRoleDto) {
        // 先删除数据库中的用户角色关系
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", assignRoleDto.getUserId());
        userRoleMapper.delete(userRoleQueryWrapper);
        // 再创建用户对应的角色关系
        // roleList 存储需要分配角色的 roleName
        List<String> roleList = assignRoleDto.getRoleCheckList();
        // [
        //  "入库",
        //  "出库",
        //  "商品",
        //  "调货",
        //  "仓库",
        //  "采购"
        //]
        int count = 0;
        for (String roleName : roleList) {
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_name", roleName);
            Role role = roleMapper.selectOne(queryWrapper);
            UserRole userRole = new UserRole();
            userRole.setRoleId(role.getRoleId());
            userRole.setUserId(assignRoleDto.getUserId());
            int i = userRoleMapper.insert(userRole);
            if (i == 1) {
                count++;
            }
        }
        System.out.println("count = " + count + "，List.size = " + roleList.size());
        if (count != roleList.size()) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "添加错误！");
        }
        return Result.ok("添加成功！");

    }
}




