package com.work.warehousemanager.dto.assignrole;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 接收给用户分配角色前端传递的数据的Dto类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssignRoleDto implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 7654260429609934608L;
    //接收请求参数userId -- 用户id
    private Integer userId;

    //接收请求参数roleCheckList -- 给用户分配的所有角色名
    private List<String> roleCheckList;
}
