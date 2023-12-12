package com.work.warehousemanager.constants;

public class RedisConstants {
    // 验证码图片 key
    public static final String LOGIN_CODE_KEY = "login:code:";
    // 过期时间 5 * 60
    public static final Long LOGIN_CODE_TTL = 300L;
    public static final Long AUTH_TTL = 7200L;
    // token
    public static final String LOGIN_TOKEN_KEY = "login:token:";
    // 用户菜单树
    public static final String LOGIN_USER_TREE_AUTH_KEY = "login:user:tree:auth:";
    // 权限树
    public static final String TREE_AUTH_ALL_KEY = "tree:auth:all";
    // 商品分类树
    public static final String TREE_PRODUCT_ALL_KEY = "tree:product:type:all";

    // 角色
    public static final String ALL_ROLE_KEY = "all:role";
    // 供货商
    public static final String ALL_SUPPLY_KEY = "all:supply";
    // 品牌
    public static final String ALL_BRAND_KEY = "all:brand";
    // 仓库
    public static final String ALL_STORE_KEY = "all:store";
    // 产地
    public static final String ALL_PLACE_KEY = "all:place";
    // 规格单位
    public static final String ALL_UNIT_KEY = "all:unit";

}
