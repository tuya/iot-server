package com.tuya.iot.suite.service.util;

import com.tuya.iot.suite.service.asset.function.ListLoopFunction;

import java.util.List;

/**
 * @author mickey
 * @date 2021年06月21日 21:25
 */
public class PageHelper {

    public static <T> boolean doListBySize(int size, List<T> ids, ListLoopFunction function) {
        boolean res = true;
        int tart = 0;
        int end = size;
        List<T> tempList = ids.subList(tart, end);
        while (tempList.size() > 0) {
            res = res && function.doListLoop(tempList);
            tart = end;
            end += size;
            if (end > ids.size()) {
                end = ids.size();
            }
            tempList = ids.subList(tart, end);
        }
        return res;
    }
}

