package com.tuya.iot.suite.util;

import com.alibaba.fastjson.JSONObject;
import com.tuya.iot.suite.model.Area;
import com.tuya.iot.suite.service.util.TreeHelper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/10
 */
public class TreeHelperTest {


    @Test
    @SneakyThrows
    public void testDummyRoot(){
        File file = ResourceUtils.getFile("classpath:area.json");
        String json = FileCopyUtils.copyToString(new FileReader(file));
        Area area = JSONObject.parseObject(json, Area.class);
        TreeHelper<String,Area> helper = new TreeHelper("code","parentCode","children",Area.class);
        area = helper.dummyRoot("+",area.getChildren());
        helper.bfs(area,a->{
            System.out.println(a.getCode());
        });
    }
}
