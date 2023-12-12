package com.work.warehousemanager.constants;

/**
 * 常量类:
 */
public interface WarehouseConstants {

    //用户未审核
    public String USER_STATE_NOT_PASS = "0";

    //用户已审核
    public String USER_STATE_PASS = "1";

    // 角色启用
    public String ROLE_STATE_PASS = "1";
    // 角色未启用
    public String ROLE_STATE_NOT_PASS = "0";
    // 商品上下架
    public String PRODUCT_STATE_UP = "1";
    public String PRODUCT_STATE_DOWN = "0";
    // 商品入库
    public String PRODUCT_IN = "1";
    public String PRODUCT_NOT_IN = "0";
    // 商品出库
    public String PRODUCT_NOT_OUT = "0";
    public String PRODUCT_IS_OUT = "1";

    // 权限启用
    public String AUTH_STATE_PASS = "1";
    // 权限未启用
    public String AUTH_STATE_NOT_PASS = "0";
    // 权限类型
    public String AUTH_TYPE_MODULE = "1";
    public String AUTH_TYPE_LIST = "2";
    public String AUTH_TYPE_BUTTON = "3";

    // 逻辑删除 默认0 (0 未删，1 删除)
    public String LOGIC_CODE = "0";

    //传递token的请求头名称
    public String HEADER_TOKEN_NAME = "Token";
    // 菜单最高级父 id
    public Integer PARENT_ID = 0;
    public Integer ORIGINAL_PASSWORD = 123456;

}
