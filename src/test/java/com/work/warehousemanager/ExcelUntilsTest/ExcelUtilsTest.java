package com.work.warehousemanager.ExcelUntilsTest;

import com.work.warehousemanager.entity.Store;
import com.work.warehousemanager.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ExcelUtilsTest {

    @Resource
    private StoreService storeService;


    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        Type type = list.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println(actualTypeArgument.getTypeName());
            }
        }
    }

    @Test
    public void test1() {


        List<Store> storeList = storeService.list();
        Store store = storeList.get(0);
        Class<? extends Store> storeClass = store.getClass();
        System.out.println("-----------------------------");
        System.out.println(storeClass.getSimpleName());

    }
}
